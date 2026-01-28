import * as React from "react";
import {useCallback, useState} from "react";
import {mayRemove} from "./person-list-utils";
import Icon from "./Icon";
import {EntityLabel} from "./entity/EntityLabel";
import {EntitySearchSelector} from "./entity-search-selector/EntitySearchSelector";
import {Person} from "../../types/Person";
import {mkRef} from "../../utils/mkRef";
import {EntityReference} from "../../types/Entity";
import personApi from "../../api/person"
import {useQuery} from "@tanstack/react-query";
import {EntityKind} from "../../enums/Entity";
import { VisualStateModes } from "../../enums/VisualState";

const searchKinds = [EntityKind.PERSON];
export interface PersonListProps {
    people: Person[];
    canAdd?: boolean;
    canRemove?: boolean;
    canRemoveSelf?: boolean;
    onAdd?: (p: Person) => Promise<any>;
    onRemove?: (p: Person) => Promise<any>;
}

export const PersonList = ({
    people = [],
    canAdd = false,
    canRemove = false,
    canRemoveSelf = true,
    onAdd = (p: Person) => Promise.resolve(),
    onRemove = (p: Person) => Promise.resolve()
}: PersonListProps) => {

    const [mode, setMode] = useState(VisualStateModes.LIST);
    const {data: self} = useQuery(personApi.getSelf());

    const initiateAddition = useCallback((e: React.MouseEvent) => {
        e.stopPropagation();
        setMode(VisualStateModes.ADD);
    }, []);

    const doAdd = useCallback((person: Person) => {
        onAdd(person)
            .then(() => setMode(VisualStateModes.LIST));
    }, [onAdd]);

    const cancelAdd = useCallback((e: React.MouseEvent) => {
        e.stopPropagation();
        setMode(VisualStateModes.LIST);
    }, []);


    const peopleList = self && people
        .map(p => ({person: p, isRemovable: mayRemove(p, self, {canRemove, canRemoveSelf})}));

    const peopleIds = people.map(d => d.id);

    function notAlreadyPicked(d: EntityReference) {
        return !peopleIds.includes(d.id);
    }

    return (
        <>
            {mode === VisualStateModes.LIST &&
                <ul className="list-unstyled">
                    {peopleList?.map(p => (
                        <li key={p.person.id} className={`waltz-visibility-parent ${p.person.isRemoved ? "removed" : ""}`}>
                            {p.isRemovable &&
                            <button className="btn-skinny remove waltz-visibility-child-10"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        onRemove(p.person);
                                    }}
                                    title="Remove person">
                                <Icon name="minus-circle" fixedWidth={true}/>
                            </button>
                            }
                            {!p.isRemovable && canRemove &&
                            <button className="btn-skinny remove"
                                    disabled>
                                <Icon name="fw"/>
                            </button>
                            }
                            <EntityLabel ref={mkRef(p.person)}
                                         showIcon={false}/>
                        </li>
                    ))}
                    {canAdd &&
                    <li className="waltz-visibility-parent">
                        <button className="btn-skinny add waltz-visibility-child-50"
                                onClick={initiateAddition}
                                title="Add an additional person">
                            <Icon name="plus-circle"/>
                            Add additional person
                        </button>
                    </li>
                    }
                </ul>
            }
            {mode === VisualStateModes.ADD &&
                <div>
                    <strong>Add additional person</strong>
                    <EntitySearchSelector entityKinds={searchKinds}
                                          onSelect={doAdd}
                                          selectionFilter={notAlreadyPicked}/>
                    <button className="btn-link"
                            onClick={cancelAdd}>
                        Cancel
                    </button>
                </div>
            }
        </>
    );
};
