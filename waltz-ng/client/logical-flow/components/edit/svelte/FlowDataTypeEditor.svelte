<script>

    import _ from "lodash";
    import {dataTypeDecoratorStore} from "../../../../svelte-stores/data-type-decorator-store";
    import toasts from "../../../../svelte-stores/toast-store";
    import {displayError} from "../../../../common/error-utils";
    import {mkSelectionOptions} from "../../../../common/selector-utils";
    import {logicalFlowStore} from "../../../../svelte-stores/logical-flow-store";
    import SuggestedDataTypeTreeSelector
        from "../../../../data-types/components/data-type-decorator-section/SuggestedDataTypeTreeSelector.svelte";
    import Icon from "../../../../common/svelte/Icon.svelte";
    import SavingPlaceholder from "../../../../common/svelte/SavingPlaceholder.svelte";
    import {dataTypeStore} from "../../../../svelte-stores/data-type-store";

    export let primaryEntityReference;
    export let onCancel = () => console.log("on cancel");
    export let onReload = () => console.log("on reload");

    const dataTypeCall = dataTypeStore.findAll();

    let selectionOptions;
    let permissionsCall;
    let flowCall;
    let decoratorsCall;
    let ratingCharacteristicsCall;
    let usageCharacteristicsCall;
    let saving = false;
    let dirty = false;
    let unknownDataTypeId;

    let workingDataTypes = [];
    let addedDataTypeIds = [];
    let removedDataTypeIds = [];

    $: allDataTypes = $dataTypeCall?.data;
    $: unknownDataTypeId = _.find(allDataTypes, dt => dt.unknown)?.id;

    function toggleDataType(evt) {
        dirty = true;
        const dataType = evt.detail;
        if (_.includes(workingDataTypes, dataType.id)) {
            workingDataTypes = _.without(workingDataTypes, dataType.id);
        } else {
            workingDataTypes = _.concat(workingDataTypes, dataType.id);
        }
        workingDataTypes = _.without(workingDataTypes, unknownDataTypeId);
    }

    function save() {

        saving = true;

        const cmd = {
            entityReference: primaryEntityReference,
            addedDataTypeIds,
            removedDataTypeIds,
        };

        dataTypeDecoratorStore.save(primaryEntityReference, cmd)
            .then(() => {
                saving = false;
                toasts.success("Successfully saved data types");
                decoratorsCall = dataTypeDecoratorStore.findBySelector("LOGICAL_DATA_FLOW", selectionOptions, true);
                onReload();
            })
            .catch(e => displayError("Could not save data type changes", e));
    }

    $: {
        if (primaryEntityReference) {
            dirty = false;
            selectionOptions = mkSelectionOptions(primaryEntityReference);
            flowCall = logicalFlowStore.getById(primaryEntityReference.id);
            decoratorsCall = dataTypeDecoratorStore.findBySelector("LOGICAL_DATA_FLOW", selectionOptions);
        }
    }


    $: {
        if (!_.isEmpty(logicalFlow)){

            const cmd = {
                source: logicalFlow.source,
                target: logicalFlow.target
            }

            usageCharacteristicsCall = dataTypeDecoratorStore.findDatatypeUsageCharacteristics(logicalFlow);
            ratingCharacteristicsCall = dataTypeDecoratorStore.findDataTypeRatingCharacteristics(cmd);
        }
    }

    $: logicalFlow = $flowCall?.data;
    $: decorators = $decoratorsCall?.data || [];
    $: dataTypes = _.map(decorators, d => d.dataTypeId);
    $: ratingCharacteristics = $ratingCharacteristicsCall?.data;
    $: usageCharacteristics = $usageCharacteristicsCall?.data;

    $: selectionFilter = (d) => !_.includes(workingDataTypes, d.id);

    $: addedDataTypeIds = _.without(workingDataTypes, ...dataTypes);
    $: removedDataTypeIds = _.without(dataTypes, ...workingDataTypes);

    $: {
        if(!dirty) {
            workingDataTypes = dataTypes;
        }
    }

</script>

<SuggestedDataTypeTreeSelector {logicalFlow}
                               selectionFilter={selectionFilter}
                               onSelect={toggleDataType}
                               {ratingCharacteristics}
                               {usageCharacteristics}/>
<div style="padding-top: 1em">
    <button class="btn btn-skinny"
            title={_.isEmpty(workingDataTypes) ? "At least one data type must be associated to this flow" : ""}
            disabled={_.isEmpty(workingDataTypes) || saving}
            on:click={save}>
        <Icon name="floppy-o"/>Save
    </button>
    |
    <button class="btn btn-skinny"
            on:click={onCancel}>
        <Icon name="ban"/>Cancel
    </button>
    {#if saving}
        <span>
            <SavingPlaceholder/>
        </span>
    {/if}
</div>