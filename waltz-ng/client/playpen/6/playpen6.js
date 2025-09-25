import template from "./playpen6.html";
import DummyComponent from "../../react-cli/components/DummyComponent";
import { initialiseData } from "../../common";

const initialState = {
    DummyComponent
};


function controller() {
    const vm = initialiseData(this, initialState);

    vm.$onInit = () => {

    }
}

export default {
    template,
    controller,
    controllerAs: "$ctrl",
    bindToController: true,
    scope: {}
};