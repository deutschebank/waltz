import React, {useEffect, useState} from "react"
import pageInfo from "../../svelte-stores/page-navigation-store";
import reduxStore from "../../redux-store";
import {incremented} from "../../redux-slices/counter-slice";
import {navigate} from "../../redux-slices/page-nav-slice";

const DummyComponent = (props) => {
    const [value, setValue] = useState(0);
    const [reduxVal, setReduxVal] = useState(reduxStore.getState().counter.value);

    const onClick = () => reduxStore.dispatch(navigate({
        state: "main.physical-flow.view",
        params: {
            id: 22
        },
        isNotification: false
    }));

    const onIncrement = () => setValue((prev) => prev + 1);
    const onIncrementRedux = () => reduxStore.dispatch(incremented());

    useEffect(() => {
        console.log("react subscribed");
        const unsubscribe = reduxStore.subscribe(() => setReduxVal(reduxStore.getState().counter.value));
        return () => {
            unsubscribe();
        };
    }, [])

    return (
        <div>
            <div style={{ background: '#f0f0f0', borderRadius: '4px', textAlign: 'center' }}>
                <p>Hello from React!</p>
                <button className="btn btn-default"
                        onClick={onIncrement}>
                    Increment: {value}
                </button>
                <button className="btn btn-default"
                        onClick={onClick}>
                    Route Me!
                </button>
                <p>{props.helloText}</p>
                <button className="btn btn-default"
                        onClick={onIncrementRedux}>
                    Increment Redux: {reduxVal}
                </button>
            </div>
        </div>
	);
};

export default DummyComponent;
