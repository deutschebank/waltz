import React from  "react"
import {useState} from "react";

const DummyComponent = (props) => {
    const [value, setValue] = useState(0);
    const onClick = () => setValue(prev => prev + 1);

    return (
        <div>
            <div style={{ background: '#f0f0f0', borderRadius: '4px', textAlign: 'center' }}>
                <p>Hello from React!</p>
                <button className="btn btn-default"
                        onClick={onClick}>
                    Increment
                </button>
                <p>{value}</p>
            </div>
        </div>
	);
};

export default DummyComponent;
