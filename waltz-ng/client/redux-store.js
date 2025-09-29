import { configureStore } from '@reduxjs/toolkit'
import counterReducer from "./redux-slices/counter-slice";
import pageNavReducer from "./redux-slices/page-nav-slice";

const store = configureStore({
    reducer: {
        counter: counterReducer,
        pageNav: pageNavReducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware({
        // ignore serializable check for page navigations
        serializableCheck: {
            ignoredActions: ['pageNaV/navigate'],
            ignoredActionPaths: ['payload.options'],
            ignoredPaths: ['pageNav.options']
        }
    })
})

export default store;