import React, {useState} from "react"
import reduxStore from "../../redux-store";
import {incremented} from "../../redux-slices/counter-slice";
import {navigate} from "../../redux-slices/page-nav-slice";
import pageInfo from "../../svelte-stores/page-navigation-store";
import {useSliceSelector} from "./common/useSliceSelector";
import Section from "./common/Section";

interface DummyComponentProps {
    helloText?: string;
}

const DummyComponent = ({
    helloText}: DummyComponentProps) => {
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
            <div style={{ background: '#f0f0f0', borderRadius: '4px', padding: '16px' }}>
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
            </div>
        </div>
	);
};

export default DummyComponent;
