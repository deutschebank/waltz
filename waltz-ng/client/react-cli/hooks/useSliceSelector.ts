import {useEffect, useState} from "react";
import reduxStore from "../../redux-store";
import {ReduxRootState} from "../types/Redux";
import _ from 'lodash';

export function useSliceSelector<T>(selector: (state: ReduxRootState) => T): T {
    const [selected, setSelected] = useState(() => selector(reduxStore.getState()));

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