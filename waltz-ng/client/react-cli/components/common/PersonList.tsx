
import * as React from "react";
import {useCallback, useState} from "react";
import _ from "lodash";
import {mayRemove} from "./person-list-utils";
import Icon from "./Icon";
import {EntityLabel} from "./entity/EntityLabel";
import {EntitySearchSelector} from "./entity-search-selector/EntitySearchSelector";
import {usePersonStore} from "../../../svelte-stores/usePersonStore";
import {Person} from "../../types/PersonTypes";


const Modes = {
    ADD: "add",
    LIST: "list"
};

const searchKinds = ["PERSON"];

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

    const [mode, setMode] = useState(Modes.LIST);
    const {data: self} = usePersonStore().getSelf();

    const initiateAddition = useCallback((e: React.MouseEvent) => {
        e.stopPropagation();
        setMode(Modes.ADD);
    }, []);

    const doAdd = useCallback((person: Person) => {
        onAdd(person)
            .then(() => setMode(Modes.LIST));
    }, [onAdd]);

    const cancelAdd = useCallback((e: React.MouseEvent) => {
        e.stopPropagation();
        setMode(Modes.LIST);
    }, []);


    const peopleList = _.map(
        people,
        p => ({
            person: p,
            isRemovable: mayRemove(p, self, {canRemove, canRemoveSelf})
        }));

    const peopleIds = _.map(people, d => d.id);

    function notAlreadyPicked(d: Person) {
        return !_.includes(peopleIds, d.id);
    }

    return (
        <>
            {mode === Modes.LIST &&
                <ul className="list-unstyled">
                    {peopleList.map(p => (
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
                            <EntityLabel ref={p.person}
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
            {mode === Modes.ADD &&
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
