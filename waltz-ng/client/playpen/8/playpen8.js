import template from "./playpen8.html";
// import DummyComponentTwo from "../../react-cli/components/DummyComponentTwo";
import { initialiseData } from "../../common";
import store from "../../redux-store";
import {incremented2} from "../../redux-slices/counter-slice-2";
import _ from 'lodash';

const initialState = {
    // DummyComponentTwo,
    reactHidden: false
};


function controller($scope) {
    const vm = initialiseData(this, initialState);

    vm.$onInit = () => {
        vm.reduxStoreValue = store.getState().counter2.value;

        console.log("ng subscribed");
        vm.unsubscribe = store.subscribe(() => {
            const newValue = store.getState().counter2.value;
            if(!_.isEqual(newValue, vm.reduxStoreValue)) {
                console.log('ng re renders')
                vm.reduxStoreValue = newValue;
                $scope.$applyAsync();
            }
        });
    }

    vm.onClick = () => vm.reactHidden = !vm.reactHidden;

    vm.onIncrement = () => store.dispatch(incremented2());

    $scope.$on("$destroy", () => {
        console.log("ng unsubscribed");
        vm.unsubscribe();
    });
}

controller.$inject = ["$scope"];

export default {
    template,
    controller,
    controllerAs: "$ctrl",
    bindToController: true,
    scope: {}
};