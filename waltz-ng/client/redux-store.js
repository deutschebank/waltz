import { configureStore } from '@reduxjs/toolkit'
import counterReducer from "./redux-slices/counter-slice";
import pageNavReducer from "./redux-slices/page-nav-slice";
import counter2Reducer from "./redux-slices/counter-slice-2";

const store = configureStore({
    reducer: {
        counter: counterReducer,
        pageNav: pageNavReducer,
        counter2: counter2Reducer
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        // ignore serializable check for page navigations
        serializableCheck: {
            ignoredActions: ['pageNav/navigate'],
            ignoredActionPaths: ['payload.options'],
            ignoredPaths: ['pageNav.options']
        }
    })
})

export default store;