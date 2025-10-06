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

interface DummyComponentProps {
    helloText?: string;
}

const DummyComponent = ({
    helloText}: DummyComponentProps) => {
    const [toggleState, setToggleState] = useState(false);
    const [value, setValue] = useState(0);
    const reduxVal = useSliceSelector(state => state.counter.value);

    const onClick = () => reduxStore.dispatch(navigate({
        state: "main.playpen.6",
        params: {
            id: 22
        },
        isNotification: false
    }));

    // const onClick = () => {
    //     pageInfo.set({
    //         state: "main.playpen.6",
    //         params: {
    //             id: 22
    //         },
    //         isNotification: false
    //     });
    // }

    const onIncrement = () => setValue((prev) => prev + 1);
    const onIncrementRedux = () => reduxStore.dispatch(incremented());

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
                         children={<div>Hello Children</div>}>
                </Section>
                <Toggle onToggle={() => setToggleState((prev) => !prev)}
                        state={toggleState}></Toggle>
                <NoData>No Data</NoData>
                <DateTime dateTime={new Date().toLocaleString()}
                            relative={false}
                            formatStr={"DD/MM/YYYY"}></DateTime>
                <SubSection header={<p>Header</p>}
                            content={<p>Content is here</p>}>
                </SubSection>
            </div>
        </div>
	);
};

export default DummyComponent;
