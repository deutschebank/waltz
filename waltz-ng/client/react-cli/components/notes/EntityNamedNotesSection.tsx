import React, {useMemo, useState} from "react";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import _ from "lodash";
import {displayError} from "../../../common/error-utils";
import {Modes} from "../../enums/User";
import {useToasts} from "../../context/toast/ToastContext";
import {EntityReference} from "../../types/Entity";
import {noteApi} from "../../api/note";
import {noteTypeApi} from "../../api/note-type";
import {NamedNote, NamedNoteType, Note} from "../../types/NamedNote";
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

type EntityNamedNotesSectionProps = {
    parentEntityRef: EntityReference;
};

const EntityNamedNotesSection: React.FC<EntityNamedNotesSectionProps> = ({parentEntityRef}) => {
    const queryClient = useQueryClient();
    const {addToast} = useToasts();

    const [mode, setMode] = useState(Modes.VIEW);
    const [selectedNote, setSelectedNote] = useState<NamedNoteType | null>(null);
    const [selectedType, setSelectedType] = useState<NamedNote["entity"] | null>(null);

    const {
        data: noteTypesWithOps = [],
        isLoading: isLoadingNoteTypes,
        error: noteTypesError,
    } = useQuery(noteTypeApi.findForRefAndUser(parentEntityRef));

    const {
        data: notes = [],
        isLoading: isLoadingNotes,
        error: notesError,
    } = useQuery(noteApi.findForEntityReference(parentEntityRef));

    function clearSelected() {
        setSelectedNote(null);
        setSelectedType(null);
    }

    function fetchReactQueries() {
        queryClient.invalidateQueries({
            queryKey: noteTypeApi.findForRefAndUser(parentEntityRef).queryKey,
        });
        queryClient.invalidateQueries({
            queryKey: noteApi.findForEntityReference(parentEntityRef).queryKey,
        });
    }

    const {mutate: removeNote} = useMutation<boolean, Error, NamedNoteType>({
        mutationFn: (note: NamedNoteType) => {
            const {mutationFn} = noteApi.remove(note.entityReference!, note.namedNoteTypeId!);
            return mutationFn();
        },
        onSuccess: () => {
            fetchReactQueries();
            addToast({type: NotificationTypeEnum.SUCCESS, message: "Removed note"});
            setMode(Modes.VIEW);
            clearSelected();
        },
        onError: (e: any) => displayError("Failed to remove note", e),
    });

    const {mutate: saveNote} = useMutation<boolean, Error, {noteTypeId: number; noteText: string}>({
        mutationFn: (vars: {noteTypeId: number; noteText: string}) => {
            const {mutationFn} = noteApi.save(parentEntityRef, vars.noteTypeId, vars.noteText);
            return mutationFn();
        },
        onSuccess: () => {
            fetchReactQueries();
            addToast({type: NotificationTypeEnum.SUCCESS, message: "Saved note"});
            setMode(Modes.VIEW);
            clearSelected();
        },
        onError: (e: any) => displayError("Failed to save note", e),
    });

    const notesWithTypes = useMemo(() => {
        const typesById = _.keyBy(noteTypesWithOps, (d) => d.entity.id);
        return _.chain(notes)
            .map((note) => {
                const typeAndOps = typesById[note.namedNoteTypeId!];
                return {
                    note,
                    type: typeAndOps?.entity,
                    operations: typeAndOps?.operations,
                };
            })
            .orderBy((d) => d.type?.name)
            .value();
    }, [notes, noteTypesWithOps]);

    const availableNoteTypes = useMemo(
        () =>
            _.chain(noteTypesWithOps)
                .filter((t) => _.includes(t.operations, "ADD"))
                .map((t) => t.entity)
                .value(),
        [noteTypesWithOps]
    );

    const onCancel = (message: string) => {
        setMode(Modes.VIEW);
        clearSelected();
        addToast({type: "INFO", message});
    };

    const handleShowRemovalConfirmation = (note: NamedNoteType) => {
        setMode(Modes.REMOVE);
        clearSelected();
        setSelectedNote(note);
    };

    const handleShowUpdatePanel = (nt: Note) => {
        setMode(Modes.UPDATE);
        setSelectedNote(nt.note);
        setSelectedType(nt.type);
    };

    const handleShowAddPanel = () => {
        setMode(Modes.ADD);
        clearSelected();
    };

    const handleUpdateNote = (updatedText: string) => {
        if (selectedNote) {
            saveNote({noteTypeId: selectedNote.namedNoteTypeId!, noteText: updatedText});
        }
    };

    const handleCreateNote = (newNote: {noteTypeId: number; noteText: string}) => {
        saveNote(newNote);
    };

    if (isLoadingNoteTypes || isLoadingNotes) {
        return <Loader />;
    }

    if (noteTypesError || notesError) {
        return <div>Error loading notes: {noteTypesError?.message || notesError?.message}</div>;
    }

    const renderContent = () => {
        switch (mode) {
            case Modes.REMOVE:
                return (
                    <EntityNamedNoteRemovalPanel
                        note={selectedNote}
                        onConfirm={() => selectedNote && removeNote(selectedNote)}
                        onCancel={() => onCancel("Cancelled removal of note")}
                    />
                );
            case Modes.ADD:
                return (
                    <EntityNamedNoteAddPanel
                        availableNoteTypes={availableNoteTypes}
                        onCreate={handleCreateNote}
                        onCancel={() => onCancel("Cancelled addition of note")}
                    />
                );
            case Modes.UPDATE:
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
            case Modes.VIEW:
            default:
                return (
                    <>
                        <table className="table table-condensed small">
                            <colgroup>
                                <col width="30%" />
                                <col width="70%" />
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>Note Type</th>
                                    <th>Note Text</th>
                                </tr>
                            </thead>
                            <tbody>
                                {notesWithTypes.length > 0 ? (
                                    notesWithTypes.map((nt) => (
                                        <tr key={nt.type?.id} className="waltz-visibility-parent">
                                            <td>
                                                <div className="note-title">{nt.type?.name}</div>
                                                <div className="small text-muted">
                                                    {nt.type?.description}
                                                </div>
                                                <div className="small text-muted">
                                                    Last Modified: <LastEdited entity={nt.note} />
                                                </div>
                                                {!_.isEmpty(nt.operations) && (
                                                    <div className="actions waltz-visibility-child-30">
                                                        {_.includes(nt.operations, "UPDATE") && (
                                                            <button
                                                                className="btn-skinny action"
                                                                onClick={() =>
                                                                    handleShowUpdatePanel(nt)
                                                                }
                                                            >
                                                                <Icon name="edit" /> Edit
                                                            </button>
                                                        )}
                                                        {_.includes(nt.operations, "REMOVE") && (
                                                            <button
                                                                className="btn-skinny action"
                                                                onClick={() =>
                                                                    handleShowRemovalConfirmation(
                                                                        nt.note
                                                                    )
                                                                }
                                                            >
                                                                <Icon name="trash" /> Delete
                                                            </button>
                                                        )}
                                                    </div>
                                                )}
                                            </td>
                                            <td>
                                                <Markdown
                                                    context={{ref: parentEntityRef}}
                                                    text={nt.note.noteText}
                                                />
                                            </td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan={2}>
                                            <NoData>
                                                <b>No Notes</b> have been added
                                            </NoData>
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                        {!_.isEmpty(availableNoteTypes) && (
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
