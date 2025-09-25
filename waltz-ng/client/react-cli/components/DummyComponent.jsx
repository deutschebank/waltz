import React from  "react"
import {useState} from "react";
import pageInfo from "../../svelte-stores/page-navigation-store";

const DummyComponent = (props) => {
    const [value, setValue] = useState(0);
    const onClick = () =>
        pageInfo.set({
            state: "main.physical-flow.view",
            params: {
                id: 22
            }
        });

    const onIncrement = () => setValue((prev) => prev + 1);

    return (
        <div>
            <div style={{ background: '#f0f0f0', borderRadius: '4px', textAlign: 'center' }}>
                <p>Hello from React!</p>
                <button className="btn btn-default"
                        onClick={onIncrement}>
                    Increment
                </button>
                <p>{value}</p>
                <button className="btn btn-default"
                        onClick={onClick}>
                    Route Me!
                </button>
                <p>{props.helloText}</p>
            </div>
        </div>
	);
};

export default DummyComponent;
