import template from "./playpen6.html";
import DummyComponent from "../../react-cli/components/DummyComponent";
import { initialiseData } from "../../common";
import store from "../../redux-store";

const initialState = {
    DummyComponent,
    reactHidden: false
};


function controller($scope) {
    const vm = initialiseData(this, initialState);

    vm.$onInit = () => {
        vm.reduxStoreValue = store.getState().counter.value;

        console.log("ng subscribed");
        vm.unsubscribe = store.subscribe(() => {
            vm.reduxStoreValue = store.getState().counter.value;
            $scope.$applyAsync();
        });
    }

    vm.onClick = () => vm.reactHidden = !vm.reactHidden;

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