import React, {useMemo, useState} from "react";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {displayError} from "../../../common/error-utils";
import {useToasts} from "../../context/toast/ToastContext";
import {EntityReference} from "../../types/Entity";
import {noteApi} from "../../api/note";
import {noteTypeApi} from "../../api/note-type";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import LastEdited from "../common/LastEdited";
import Loader from "../common/loader/Loader";
import Markdown from "../common/markdown/Markdown";
import NoData from "../common/no-data/NoData";
import EntityNamedNoteAddPanel from "./EntityNamedNoteAddPanel";
import EntityNamedNoteEditPanel from "./EntityNamedNoteEditPanel";
import EntityNamedNoteRemovalPanel from "./EntityNamedNoteRemovalPanel";
import {NotificationTypeEnum} from "../../enums/Notification";
import {VisualStateModes} from "../../enums/VisualState";
import {
  Note,
  NoteWithType,
  NoteTypeEntity,
  NamedNoteWithOperations,
} from "../../types/NamedNote";
import EntityNamedNotesGrid from "./EntityNamedNotesGrid";

type EntityNamedNotesSectionProps = {
  parentEntityRef: EntityReference;
};

const EntityNamedNotesSection: React.FC<EntityNamedNotesSectionProps> = ({
  parentEntityRef,
}) => {
  const queryClient = useQueryClient();
  const {addToast} = useToasts();

  const [mode, setMode] = useState<VisualStateModes>(VisualStateModes.VIEW);
  const [selectedNote, setSelectedNote] = useState<Note | null>(null);
  const [selectedType, setSelectedType] = useState<NoteTypeEntity | null>(null);

  // Fetches available note types for the user and entity.
  const {
    data: noteTypesWithOps = [],
    isLoading: isLoadingNoteTypes,
    error: noteTypesError,
  } = useQuery(noteTypeApi.findForRefAndUser(parentEntityRef));

  // Fetches existing notes for the entity.
  const {
    data: notes = [],
    isLoading: isLoadingNotes,
    error: notesError,
  } = useQuery(noteApi.findForEntityReference(parentEntityRef));

  function clearSelected() {
    setSelectedNote(null);
    setSelectedType(null);
  }

  /**
   * Invalidates and refetches note and note type queries to refresh data.
   */
  function fetchReactQueries() {
    queryClient.invalidateQueries({
      queryKey: noteTypeApi.findForRefAndUser(parentEntityRef).queryKey,
    });
    queryClient.invalidateQueries({
      queryKey: noteApi.findForEntityReference(parentEntityRef).queryKey,
    });
  }

  /**
   * Mutation to remove a note.
   */
  const {mutate: removeNote} = useMutation<boolean, Error, Note>({
    mutationFn: (note: Note) => {
      const {mutationFn} = noteApi.remove(note.entityReference!, note.namedNoteTypeId!);
      return mutationFn();
    },
    onSuccess: () => {
      fetchReactQueries();
      addToast({type: NotificationTypeEnum.SUCCESS, message: "Removed note"});
      setMode(VisualStateModes.VIEW);
      clearSelected();
    },
    onError: (e) => displayError("Failed to remove note", e),
  });

  /**
   * Mutation to save (create or update) a note.
   */
  const {mutate: saveNote} = useMutation<
    boolean,
    Error,
    {noteTypeId: number; noteText: string; action: "create" | "update"}
  >({
    mutationFn: (vars) => {
      const {mutationFn} = noteApi.save(parentEntityRef, vars.noteTypeId, vars.noteText);
      return mutationFn();
    },
    onSuccess: (_data, variables) => {
      fetchReactQueries();
      const message = variables.action === "update" ? "Updated note" : "Saved note";
      addToast({type: NotificationTypeEnum.SUCCESS, message});
      setMode(VisualStateModes.VIEW);
      clearSelected();
    },
    onError: (e) => displayError("Failed to save note", e),
  });

  /**
   * Combines notes with their corresponding type information.
   */
  const notesWithTypes = useMemo(() => {
    const typesById = noteTypesWithOps.reduce<Record<string, NamedNoteWithOperations>>(
      (acc, d) => {
        acc[d.entity.id] = d;
        return acc;
      },
      {}
    );

    return notes
      .map((note) => {
        const typeAndOps = typesById[note.namedNoteTypeId!];
        return {
          note,
          type: typeAndOps?.entity,
          operations: typeAndOps?.operations,
        };
      })
      .sort((a, b) => (a.type?.name ?? "").localeCompare(b.type?.name ?? ""));
  }, [notes, noteTypesWithOps]);

  /**
   * Filters note types to find which ones are available for the user to add.
   */
  const availableNoteTypes = useMemo(
    () =>
      noteTypesWithOps.filter((t) => t.operations.includes("ADD")).map((t) => t.entity),
    [noteTypesWithOps]
  );

  const onCancel = (message: string) => {
    setMode(VisualStateModes.VIEW);
    clearSelected();
    addToast({type: "INFO", message});
  };

  const handleShowRemovalConfirmation = (note: Note) => {
    setMode(VisualStateModes.REMOVE);
    clearSelected();
    setSelectedNote(note);
  };

  const handleShowUpdatePanel = (nt: NoteWithType) => {
    setMode(VisualStateModes.UPDATE);
    setSelectedNote(nt.note);
    setSelectedType(nt.type);
  };

  const handleShowAddPanel = () => {
    setMode(VisualStateModes.ADD);
    clearSelected();
  };

  const handleUpdateNote = (updatedText: string) => {
    if (selectedNote) {
      saveNote({
        noteTypeId: selectedNote.namedNoteTypeId!,
        noteText: updatedText,
        action: "update",
      });
    }
  };

  /**
   * Calls the saveNote mutation to create a new note.
   */
  const handleCreateNote = (newNote: {noteTypeId: number; noteText: string}) => {
    saveNote({...newNote, action: "create"});
  };

  if (isLoadingNoteTypes || isLoadingNotes) {
    return <Loader />;
  }

  if (noteTypesError || notesError) {
    return (
      <div>Error loading notes: {noteTypesError?.message || notesError?.message}</div>
    );
  }

  /**
   * Renders the appropriate panel based on the current mode (VIEW, REMOVE, ADD, UPDATE).
   */
  const renderContent = () => {
    switch (mode) {
      case VisualStateModes.REMOVE:
        return (
          <EntityNamedNoteRemovalPanel
            note={selectedNote}
            onConfirm={() => selectedNote && removeNote(selectedNote)}
            onCancel={() => onCancel("Cancelled removal of note")}
          />
        );
      case VisualStateModes.ADD:
        return (
          <EntityNamedNoteAddPanel
            availableNoteTypes={availableNoteTypes}
            onCreate={handleCreateNote}
            onCancel={() => onCancel("Cancelled addition of note")}
          />
        );
      case VisualStateModes.UPDATE:
        return (
          selectedNote &&
          selectedType && (
            <EntityNamedNoteEditPanel
              note={selectedNote}
              type={selectedType}
              onSave={handleUpdateNote}
              onCancel={() => onCancel("Cancelled update of note")}
            />
          )
        );
      case VisualStateModes.VIEW:
      default:
        return (
          <>
            <EntityNamedNotesGrid
              parentEntityRef={parentEntityRef}
              notesWithTypes={notesWithTypes}
              onEdit={handleShowUpdatePanel}
              onDelete={handleShowRemovalConfirmation}
            />
            {availableNoteTypes && availableNoteTypes.length > 0 && (
              <>
                <p>Additional note types are available for use.</p>
                <Button
                  className="btn btn-sm btn-default"
                  onClick={handleShowAddPanel}
                  style={{display: "block"}}
                >
                  <Icon name="plus" /> Add a new note
                </Button>
              </>
            )}
          </>
        );
    }
  };

  return <div>{renderContent()}</div>;
};

export default EntityNamedNotesSection;
