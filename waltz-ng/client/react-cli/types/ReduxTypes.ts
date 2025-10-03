import reduxStore from "../../redux-store";
export type ReduxStore = typeof reduxStore;
export type ReduxRootState = ReturnType<ReduxStore["getState"]>;
export type ReduxDispatch = ReduxStore["dispatch"];