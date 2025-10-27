import React, {useState} from "react"
import reduxStore from "../../redux-store";
import {incremented} from "../../redux-slices/counter-slice";
import {navigate} from "../../redux-slices/page-nav-slice";
import {useSliceSelector} from "../hooks/useSliceSelector";
import Section from "./common/Section";
import Toggle from "./common/toggle/Toggle";
import NoData from "./common/no-data/NoData";
import DateTime from "./common/DateTime";
import SubSection from "./common/sub-section/SubSection";
import {PersonList} from "./common/PersonList";
import {Person} from "../types/Person";
import PageHeader from "./common/page-header/PageHeader";
import ViewLink from "./common/view-link/ViewLink";
import {EntityKind} from "../enums/Entity";
import {addToast} from "../../redux-slices/toast-slice";
import {mkToast} from "../utils/mkToast";
import {NotificationTypeEnum} from "../enums/Notification";
import {ToastCreateType, ToastType} from "../types/Toast";
import {BreadCrumbsConfig} from "../types/BreadCrumbs";

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
        kind: EntityKind.PERSON,
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
        kind: EntityKind.PERSON,
        userId: "janesmith"
    }
];

const breadCrumbsConfig: BreadCrumbsConfig[] = [
    {state: "main.home", text: "Home"},
    {state: "main.playpen", text: "Playpen"},
    {href: "/actor/list", text: "This actor route won't work on tomcat"},
    {href: "actor/list", text: "This actor route will work on tomcat"},
    {text: "Dummy Component"}
];

// export default function DummyComponent(props: DummyComponentProps) {
//     return (
//         <QueryClientProvider client={queryClient}>
//             <DummyComponentInternal {...props}/>
//         </QueryClientProvider>
//     );
// }
// query client provider has been moved to react-component.js -> to reduce boilerplate

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

    const toastsPlaceholders: ToastCreateType[] = [
        {type:NotificationTypeEnum.SUCCESS, message:"this is a toast lorem ipsum dolor sit amet alkjfgqsfua absfuasf  asfusagf abfuisfas bafiusgfas basfuigsafs asgfuisagf"},
        {type:NotificationTypeEnum.INFO, message:"this is a toast lorem ipsum dolor sit amet alkjfgqsfua absfuasf  asfusagf abfuisfas bafiusgfas basfuigsafs asgfuisagf alkjfgqsfua absfuasf  asfusagf abfuisfas bafiusgfas basfuigsafs asgfuisagf alkjfgqsfua absfuasf  asfusagf abfuisfas bafiusgfas basfuigsafs asgfuisagf"}
    ];

    mkToast(toastsPlaceholders[0]);

    setTimeout(() => mkToast(toastsPlaceholders[1]), 500);
    setTimeout(() => mkToast(toastsPlaceholders[1]), 1000);
    setTimeout(() => mkToast(toastsPlaceholders[1]), 1500);
    setTimeout(() => mkToast(toastsPlaceholders[1]), 2000);

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
                    canRemove={false}
                    onAdd={onAddPerson}
                    onRemove={onRemovePerson}/>
                <PageHeader name="Section"
                            small="small-section"
                            breadcrumbs={breadCrumbsConfig}
                            summary={<h4>This is a summary</h4>}></PageHeader>
            </div>
        </div>
	);
};


export default DummyComponent;