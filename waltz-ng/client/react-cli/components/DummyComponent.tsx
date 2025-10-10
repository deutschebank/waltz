import React, {useState} from "react"
import reduxStore from "../../redux-store";
import {incremented} from "../../redux-slices/counter-slice";
import {navigate} from "../../redux-slices/page-nav-slice";
import pageInfo from "../../svelte-stores/page-navigation-store";
import {useSliceSelector} from "../utils/useSliceSelector";
import Section from "./common/Section";
import Toggle from "./common/toggle/Toggle";
import NoData from "./common/no-data/NoData";
import DateTime from "./common/DateTime";
import SubSection from "./common/sub-section/SubSection";
import {PersonList} from "./common/PersonList";
import {Person} from "../types/PersonTypes";
import PageHeader from "./common/page-header/PageHeader";
import {marginLeft} from "html2canvas/dist/types/css/property-descriptors/margin";
import ActorListView from "./actor/ActorListView";
import ViewLink from "./common/view-link/ViewLink";

interface DummyComponentProps {
    helloText?: string;
}

const samplePeople: Person[] = [
    {
        id: 1,
        name: "John Doe",
        displayName: "John Doe",
        email: "john.doe@example.com",
        employeeId: "12345",
        isRemoved: false,
        personKind: "EMPLOYEE",
        kind: "PERSON",
        userId: "johndoe"
    },
    {
        id: 2,
        name: "Jane Smith",
        displayName: "Jane Smith",
        email: "jane.smith@example.com",
        employeeId: "67890",
        isRemoved: false,
        personKind: "CONTRACTOR",
        kind: "PERSON",
        userId: "janesmith"
    }
];

const breadCrumbs = (
    <ol className="waltz-breadcrumbs">
        <li><ViewLink state="main.home">{"Home"}</ViewLink></li>
        <li><ViewLink state="main.playpen">{"Playpen"}</ViewLink></li>
        <li><a href="">Home</a></li>
        <li><a href="/">This route wont work as expected</a></li>
        <li>Dummy Component</li>
    </ol>
);

const DummyComponent = ({
    helloText}: DummyComponentProps) => {
    const [toggleState, setToggleState] = useState(false);
    const [value, setValue] = useState(0);
    const [people, setPeople] = useState<Person[]>(samplePeople);
    const reduxVal = useSliceSelector(state => state.counter.value);

    const onClick = () => reduxStore.dispatch(navigate({
        state: "main.playpen",
        params: {
            id: 22
        },
        isNotification: false
    }));

    const onIncrement = () => setValue((prev) => prev + 1);
    const onIncrementRedux = () => reduxStore.dispatch(incremented());

    const onAddPerson = (person: Person) => {
        console.log("Adding person: ", person);
        setPeople(prev => [...prev, person]);
        return Promise.resolve();
    }

    const onRemovePerson = (person: Person) => {
        console.log("Removing person: ", person);
        setPeople(prev => prev.filter(p => p.id !== person.id));
        return Promise.resolve();
    }


    console.log("Rendering React");

    return (
        <div>
            <div style={{ borderRadius: '4px', padding: '16px' }}>
                <p>Hello from React!</p>
                <button className="btn btn-default"
                        onClick={onIncrement}>
                    Increment: {value}
                </button>
                <button className="btn btn-default"
                        onClick={onClick}>
                    Route Me!
                </button>
                <p>{helloText}</p>
                <button className="btn btn-default"
                        onClick={onIncrementRedux}>
                    Increment Redux: {reduxVal}
                </button>
                <Section icon="pencil-square-o"
                         name="React Section"
                         small="Small Text"
                         children={<div>Hello Children</div>}/>
                <Toggle onToggle={() => setToggleState((prev) => !prev)}
                        state={toggleState}></Toggle>
                <NoData>No Data</NoData>x
                <DateTime dateTime={new Date().toLocaleString()}
                            relative={false}
                            formatStr={"DD/MM/YYYY"}></DateTime>
                <SubSection header={<p>Header</p>}
                            content={<p>Content is here</p>}/>
                <PersonList
                    people={people}
                    canAdd={true}
                    canRemove={true}
                    onAdd={onAddPerson}
                    onRemove={onRemovePerson}/>
                <PageHeader name="Section"
                            small="small-section"
                            breadcrumbs={breadCrumbs}
                            summary={<h4>This is a summary</h4>}></PageHeader>
            </div>
        </div>
	);
};

export default DummyComponent;
