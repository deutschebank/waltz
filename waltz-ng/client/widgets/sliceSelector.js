import {useEffect, useState} from "react";
import reduxStore from "../redux-store";
import _ from 'lodash';

export const sliceSelector = (selector) => {
    const [selected, setSelected] = useState(selector(reduxStore.getState()));

    useEffect(() => {
        return reduxStore.subscribe(() => {
            const currentVal = selector(reduxStore.getState());
            if (!_.isEqual(selected, currentVal)) {
                setSelected(currentVal);
            }
        })
    }, []);


    return selected;
}