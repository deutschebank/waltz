# Svelte Component Dependency Tree

**system/svelte/analytics-dashboard/AccessLogsChartView.svelte**

```
├── SubSection.svelte
└── LineChart.svelte
    ├── LoadingPlaceholder.svelte
    │   └── Icon.svelte
    └── NoData.svelte
```

**process-diagram/components/process-diagram/svg-elems/Activity.svelte**

_No dependencies_

**process-diagram/components/process-diagram/context-panels/ActivityContextPanel.svelte**

```
├── EntityInfoPanel.svelte
│   ├── ApplicationInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── ChangeInitiativeInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── MeasurableInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── ActorInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── DataTypeInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── LogicalDataFlowInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   └── KeyAssessmentInfoPanel.svelte
│   ├── SurveyInstanceInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── DateTime.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   └── DatePicker.svelte
│   │       └── Icon.svelte
│   ├── PersonInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── Icon.svelte
│   └── EndUserApplicationInfoPanel.svelte
│       └── DescriptionFade.svelte
│           ├── Markdown.svelte
│           └── Icon.svelte
└── ConnectionsSubContextPanel.svelte
```

**common/svelte/info-panels/ActorInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── KeyInvolvementInfoPanel.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── KeyAssessmentInfoPanel.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**actor/components/svelte/ActorListView.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── DataExtractLink.svelte
    └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddAnnotationSubPanel.svelte**

_No dependencies_

**entity-diagrams/components/entity-overlay-diagrams/builder-controls/AddItemsPanel.svelte**

```
├── EntityPicker.svelte
│   ├── InvolvementPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── CostKindPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── SurveyQuestionPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── Toggle.svelte
│   │       └── Icon.svelte
│   ├── AssessmentDefinitionPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── PersonPicker.svelte
│   │   ├── Icon.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── SurveyInstanceFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   ├── NoData.svelte
│   │   └── Toggle.svelte
│   │       └── Icon.svelte
│   ├── ApplicationFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── ChangeInitiativeFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── AppGroupPicker.svelte
│   │   ├── Icon.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── DataTypePicker.svelte
│   │   └── DataTypeTreeSelector.svelte
│   │       ├── DataTypeTreeNode.svelte
│   │       │   ├── Icon.svelte
│   │       │   ├── RatingCharacteristicsDecorator.svelte
│   │       │   │   └── FlowRatingCell.svelte
│   │       │   ├── UsageCharacteristicsDecorator.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── DataTypeNodeTooltipContent.svelte
│   │       │   │   ├── Icon.svelte
│   │       │   │   ├── RatingIndicatorCell.svelte
│   │       │   │   │   └── Icon.svelte
│   │       │   │   ├── DescriptionFade.svelte
│   │       │   │   │   ├── Markdown.svelte
│   │       │   │   │   └── Icon.svelte
│   │       │   │   ├── NoData.svelte
│   │       │   │   └── Markdown.svelte
│   │       │   └── Tooltip.svelte
│   │       └── SearchInput.svelte
│   │           └── Icon.svelte
│   ├── AttestationPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── OrgUnitFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── TagPicker.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── AliasPicker.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── ComplexityKindPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── MeasurableCategoryPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── MeasurableTreeSelector.svelte
│   │       ├── MeasurableTreeNode.svelte
│   │       │   └── Icon.svelte
│   │       └── SearchInput.svelte
│   │           └── Icon.svelte
│   ├── EntityStatisticPicker.svelte
│   │   └── EntityStatisticTreeSelector.svelte
│   │       ├── SearchInput.svelte
│   │       │   └── Icon.svelte
│   │       └── StatisticTreeNode.svelte
│   │           └── Icon.svelte
│   └── MeasurablePicker.svelte
│       ├── Icon.svelte
│       ├── MeasurableTreeSelector.svelte
│       │   ├── MeasurableTreeNode.svelte
│       │   │   └── Icon.svelte
│       │   └── SearchInput.svelte
│       │       └── Icon.svelte
│       ├── Grid.svelte
│       │   └── Icon.svelte
│       └── NoData.svelte
├── DropdownPicker.svelte
│   └── Icon.svelte
├── ColorPicker.svelte
└── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddLogicalFlowSubPanel.svelte**

```
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**report-grid/components/svelte/person-edit-panel/AddNewSubscriberPanel.svelte**

```
├── Icon.svelte
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddNodeSubPanel.svelte**

```
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddOverlayGroupEntrySubPanel.svelte**

```
├── GroupSelectorPanel.svelte
│   ├── EntitySearchSelector.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── MeasurableAlignmentTreeSelector.svelte
│       ├── SearchInput.svelte
│       │   └── Icon.svelte
│       └── MeasurableAlignmentTreeNode.svelte
│           └── Icon.svelte
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── EditOverlayIconSubPanel.svelte
    ├── Icon.svelte
    ├── ColorPicker.svelte
    ├── OverlayGlyph.svelte
    ├── SymbolPicker.svelte
    └── EntityLink.svelte
        ├── ViewLink.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddPhysicalFlowSubPanel.svelte**

```
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddRelatedChangeInitiativeSubPanel.svelte**

```
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddRelatedDataTypeSubPanel.svelte**

```
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AddRelatedMeasurableSubPanel.svelte**

```
├── Icon.svelte
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/aggregated-entities/AggregatedEntitiesOverlayCell.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/AggregatedEntitiesOverlayParameters.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/aggregated-entities/AggregatedEntitiesWidgetParameters.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/AggregateOverlayDiagram.svelte**

```
└── Callout.svelte
```

**aggregate-overlay-diagram/components/context-panel/AggregateOverlayDiagramContextPanel.svelte**

```
├── DiagramInstanceSelector.svelte
│   ├── LastEdited.svelte
│   │   └── DateTime.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── InstanceCreatePanel.svelte
│       └── Icon.svelte
└── CustomiseOverlayPanel.svelte
    ├── WidgetSelector.svelte
    │   └── Icon.svelte
    ├── CreatePresetPanel.svelte
    │   └── Icon.svelte
    ├── PresetSelector.svelte
    │   ├── NoData.svelte
    │   └── Markdown.svelte
    ├── Icon.svelte
    ├── NoData.svelte
    └── FilterPanel.svelte
        ├── FilterSelectorPanel.svelte
        │   ├── AssessmentRatingPicker.svelte
        │   │   ├── AssessmentDefinitionPicker.svelte
        │   │   │   ├── Grid.svelte
        │   │   │   │   └── Icon.svelte
        │   │   │   └── Icon.svelte
        │   │   ├── RatingPicker.svelte
        │   │   │   ├── RatingIndicatorCell.svelte
        │   │   │   │   └── Icon.svelte
        │   │   │   └── Icon.svelte
        │   │   └── Markdown.svelte
        │   └── Icon.svelte
        ├── FilterList.svelte
        │   ├── RatingIndicatorCell.svelte
        │   │   └── Icon.svelte
        │   └── Icon.svelte
        └── Icon.svelte
```

**aggregate-overlay-diagram/components/context-panel/AggregateOverlayDiagramInstanceContextPanel.svelte**

```
├── CalloutList.svelte
│   ├── Icon.svelte
│   ├── Markdown.svelte
│   ├── NoData.svelte
│   ├── CalloutCreatePanel.svelte
│   │   ├── Icon.svelte
│   │   └── ColorPicker.svelte
│   └── CalloutDeletePanel.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── ImageDownloadLink.svelte
    └── Icon.svelte
```

**aggregate-overlay-diagram/components/panel/AggregateOverlayDiagramPanel.svelte**

```
├── AggregateOverlayDiagram.svelte
│   └── Callout.svelte
├── DiagramSelector.svelte
│   ├── LastEdited.svelte
│   │   └── DateTime.svelte
│   └── Icon.svelte
├── NoData.svelte
├── AggregateOverlayDiagramContextPanel.svelte
│   ├── DiagramInstanceSelector.svelte
│   │   ├── LastEdited.svelte
│   │   │   └── DateTime.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── InstanceCreatePanel.svelte
│   │       └── Icon.svelte
│   └── CustomiseOverlayPanel.svelte
│       ├── WidgetSelector.svelte
│       │   └── Icon.svelte
│       ├── CreatePresetPanel.svelte
│       │   └── Icon.svelte
│       ├── PresetSelector.svelte
│       │   ├── NoData.svelte
│       │   └── Markdown.svelte
│       ├── Icon.svelte
│       ├── NoData.svelte
│       └── FilterPanel.svelte
│           ├── FilterSelectorPanel.svelte
│           │   ├── AssessmentRatingPicker.svelte
│           │   │   ├── AssessmentDefinitionPicker.svelte
│           │   │   │   ├── Grid.svelte
│           │   │   │   │   └── Icon.svelte
│           │   │   │   └── Icon.svelte
│           │   │   ├── RatingPicker.svelte
│           │   │   │   ├── RatingIndicatorCell.svelte
│           │   │   │   │   └── Icon.svelte
│           │   │   │   └── Icon.svelte
│           │   │   └── Markdown.svelte
│           │   └── Icon.svelte
│           ├── FilterList.svelte
│           │   ├── RatingIndicatorCell.svelte
│           │   │   └── Icon.svelte
│           │   └── Icon.svelte
│           └── Icon.svelte
├── Icon.svelte
├── ImageDownloadLink.svelte
│   └── Icon.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**common/svelte/AliasControl.svelte**

```
└── TagsInput.svelte
```

**report-grid/components/svelte/pickers/AliasPicker.svelte**

```
├── Icon.svelte
└── NoData.svelte
```

**logical-flow/components/aligned-data-types-list/AlignedDataTypesList.svelte**

```
└── FlowRatingCell.svelte
```

**common/svelte/entity-pickers/AllocationSchemePicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**system/svelte/analytics-dashboard/AnalyticsDashboardAdminView.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── SubSection.svelte
├── DropdownPicker.svelte
│   └── Icon.svelte
├── GridWithCellRenderer.svelte
│   └── Icon.svelte
├── ViewLinkLabelled.svelte
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
├── AccessLogsChartView.svelte
│   ├── SubSection.svelte
│   └── LineChart.svelte
│       ├── LoadingPlaceholder.svelte
│       │   └── Icon.svelte
│       └── NoData.svelte
└── ChangeLogsChartView.svelte
    ├── SubSection.svelte
    ├── LineChart.svelte
    │   ├── LoadingPlaceholder.svelte
    │   │   └── Icon.svelte
    │   └── NoData.svelte
    └── DropdownPicker.svelte
        └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/Annotation.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/AnnotationLayer.svelte**

```
└── Annotation.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/AnnotationPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── AddAnnotationSubPanel.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/app-costs/AppCostOverlayCell.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/app-costs/AppCostWidgetParameters.svelte**

```
├── AllocationSchemePicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
└── CostKindPicker.svelte
    ├── Grid.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/app-counts/AppCountOverlayCell.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/app-counts/AppCountWidgetParameters.svelte**

_No dependencies_

**applications/pages/edit/AppEdit.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**common/svelte/entity-pickers/AppGroupPicker.svelte**

```
├── Icon.svelte
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/ApplicationChangesOverlay.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/ApplicationChangesOverlayParameters.svelte**

_No dependencies_

**process-diagram/components/process-diagram/context-panels/ApplicationContextPanel.svelte**

```
└── EntityInfoPanel.svelte
    ├── ApplicationInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── KeyInvolvementInfoPanel.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── KeyAssessmentInfoPanel.svelte
    │   └── DescriptionFade.svelte
    │       ├── Markdown.svelte
    │       └── Icon.svelte
    ├── ChangeInitiativeInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── KeyInvolvementInfoPanel.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── KeyAssessmentInfoPanel.svelte
    │   └── DescriptionFade.svelte
    │       ├── Markdown.svelte
    │       └── Icon.svelte
    ├── MeasurableInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── KeyInvolvementInfoPanel.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── KeyAssessmentInfoPanel.svelte
    │   └── DescriptionFade.svelte
    │       ├── Markdown.svelte
    │       └── Icon.svelte
    ├── ActorInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── KeyInvolvementInfoPanel.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── KeyAssessmentInfoPanel.svelte
    │   └── DescriptionFade.svelte
    │       ├── Markdown.svelte
    │       └── Icon.svelte
    ├── DataTypeInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── KeyInvolvementInfoPanel.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── KeyAssessmentInfoPanel.svelte
    │   └── DescriptionFade.svelte
    │       ├── Markdown.svelte
    │       └── Icon.svelte
    ├── LogicalDataFlowInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── KeyInvolvementInfoPanel.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   └── KeyAssessmentInfoPanel.svelte
    ├── SurveyInstanceInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── DateTime.svelte
    │   ├── DescriptionFade.svelte
    │   │   ├── Markdown.svelte
    │   │   └── Icon.svelte
    │   └── DatePicker.svelte
    │       └── Icon.svelte
    ├── PersonInfoPanel.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   └── Icon.svelte
    └── EndUserApplicationInfoPanel.svelte
        └── DescriptionFade.svelte
            ├── Markdown.svelte
            └── Icon.svelte
```

**report-grid/components/svelte/pickers/ApplicationFieldPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
└── NoData.svelte
```

**common/svelte/info-panels/ApplicationInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── KeyInvolvementInfoPanel.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── KeyAssessmentInfoPanel.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**applications/components/app-list-page-header/AppListPageHeader.svelte**

```
├── ViewLink.svelte
└── PageHeader.svelte
    └── Icon.svelte
```

**applications/components/apps-view-grid/AppsViewGrid.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── DataExtractLink.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**data-flow/components/svelte/Arcs.svelte**

_No dependencies_

**system/svelte/assessment-definitions/AssessmentDefinitionEditor.svelte**

```
└── Icon.svelte
```

**common/svelte/entity-pickers/AssessmentDefinitionPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**system/svelte/assessment-definitions/AssessmentDefinitionRemovalConfirmation.svelte**

```
└── Icon.svelte
```

**system/svelte/assessment-definitions/AssessmentDefinitionsAdminView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntityIcon.svelte
│   └── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── Icon.svelte
├── AssessmentDefinitionEditor.svelte
│   └── Icon.svelte
├── AssessmentDefinitionRemovalConfirmation.svelte
│   └── Icon.svelte
└── ItemPreviewBar.svelte
```

**assessments/components/list/AssessmentDefinitionTooltipContent.svelte**

```
├── Markdown.svelte
└── Icon.svelte
```

**legal-entity-relationship-kind/components/bulk-upload/AssessmentErrorTooltip.svelte**

_No dependencies_

**assessments/components/favourites-list/AssessmentFavouritesList.svelte**

```
├── Icon.svelte
├── Tooltip.svelte
├── AssessmentRatingTooltipContent.svelte
│   ├── Markdown.svelte
│   ├── Icon.svelte
│   └── LastEdited.svelte
│       └── DateTime.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── AssessmentDefinitionTooltipContent.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**data-flow/components/svelte/AssessmentFilters.svelte**

```
├── Icon.svelte
└── NoData.svelte
```

**data-flow/components/svelte/flow-detail-tab/filters/AssessmentFilters.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── NoData.svelte
└── EntityIcon.svelte
    └── Icon.svelte
```

**data-types/components/data-type-decorator-section/filters/AssessmentFilters.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── NoData.svelte
└── EntityIcon.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/assessment/AssessmentOverlay.svelte**

```
├── BarGraph.svelte
└── BoxGraph.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/assessments/AssessmentOverlayCell.svelte**

```
├── BarGraphCell.svelte
│   └── RatingIndicatorCell.svelte
│       └── Icon.svelte
└── BoxGraphCell.svelte
    └── RatingIndicatorCell.svelte
        └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/assessments/AssessmentOverlayLegend.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/assessment/AssessmentOverlayLegendDetail.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/assessment/AssessmentOverlayParameters.svelte**

```
├── AssessmentDefinitionPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**assessments/components/rating-editor/AssessmentRatingEditor.svelte**

```
├── Markdown.svelte
├── Icon.svelte
├── SubSection.svelte
├── RatingListView.svelte
│   ├── NoData.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── RatingDetailView.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── LastEdited.svelte
│   │   └── DateTime.svelte
│   ├── TextEditableField.svelte
│   │   ├── Markdown.svelte
│   │   ├── SavingPlaceholder.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   ├── EditableRatingValue.svelte
│   │   ├── Icon.svelte
│   │   ├── SavingPlaceholder.svelte
│   │   │   └── Icon.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   └── RatingItemDropdownPicker.svelte
│   └── Markdown.svelte
├── RemoveRatingConfirmationPanel.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── RatingAddView.svelte
│   ├── Icon.svelte
│   └── Markdown.svelte
└── NoData.svelte
```

**assessments/components/list/AssessmentRatingList.svelte**

```
├── AssessmentRatingListGroup.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── AssessmentRatingTooltipContent.svelte
│       ├── Markdown.svelte
│       ├── Icon.svelte
│       └── LastEdited.svelte
│           └── DateTime.svelte
├── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**assessments/components/list/AssessmentRatingListGroup.svelte**

```
├── Icon.svelte
├── Tooltip.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── AssessmentDefinitionTooltipContent.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── AssessmentRatingTooltipContent.svelte
    ├── Markdown.svelte
    ├── Icon.svelte
    └── LastEdited.svelte
        └── DateTime.svelte
```

**common/svelte/AssessmentRatingPicker.svelte**

```
├── AssessmentDefinitionPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── RatingPicker.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
└── Markdown.svelte
```

**measurable-rating/svelte/AssessmentRatingsMiniTable.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**assessments/components/list/AssessmentRatingTooltipContent.svelte**

```
├── Markdown.svelte
├── Icon.svelte
└── LastEdited.svelte
    └── DateTime.svelte
```

**data-flow/components/svelte/flow-detail-tab/widgets/AssessmentsTable.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── EntityIcon.svelte
    └── Icon.svelte
```

**assessments/components/info-tile/AssessmentWidget.svelte**

```
├── DropdownPicker.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/assessments/AssessmentWidgetParameters.svelte**

```
├── AssessmentDefinitionPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/attestations/AttestationOverlayCell.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**report-grid/components/svelte/pickers/AttestationPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/attestations/AttestationWidgetParameters.svelte**

```
└── DatePicker.svelte
    └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/backing-entities/BackingEntitiesPlainOverlayCell.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/backing-entities/BackingEntitiesSVGOverlayCell.svelte**

```
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/backing-entities/BackingEntitiesWidgetParameters.svelte**

_No dependencies_

**system/svelte/analytics-dashboard/BarChart.svelte**

```
└── LoadingPlaceholder.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/assessment/BarGraph.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/assessments/BarGraphCell.svelte**

```
└── RatingIndicatorCell.svelte
    └── Icon.svelte
```

**physical-flows/svelte/BasisOffsetSelect.svelte**

_No dependencies_

**system/svelte/nav-aid-builder/custom/control/BlockEditor.svelte**

_No dependencies_

**bookmarks/svelte/BookmarkCategoryMenu.svelte**

```
└── Icon.svelte
```

**bookmarks/svelte/BookmarkEditor.svelte**

_No dependencies_

**bookmarks/svelte/BookmarkListItem.svelte**

```
├── Icon.svelte
├── MiniActions.svelte
│   └── Icon.svelte
├── DateTime.svelte
└── LastEdited.svelte
    └── DateTime.svelte
```

**bookmarks/svelte/BookmarkPanel.svelte**

```
├── BookmarkCategoryMenu.svelte
│   └── Icon.svelte
├── BookmarkEditor.svelte
├── BookmarkTable.svelte
│   └── BookmarkListItem.svelte
│       ├── Icon.svelte
│       ├── MiniActions.svelte
│       │   └── Icon.svelte
│       ├── DateTime.svelte
│       └── LastEdited.svelte
│           └── DateTime.svelte
├── BookmarkRemovalConfirmation.svelte
├── Icon.svelte
├── NoData.svelte
├── SearchInput.svelte
│   └── Icon.svelte
└── LoadingPlaceholder.svelte
    └── Icon.svelte
```

**bookmarks/svelte/BookmarkRemovalConfirmation.svelte**

_No dependencies_

**bookmarks/svelte/BookmarkTable.svelte**

```
└── BookmarkListItem.svelte
    ├── Icon.svelte
    ├── MiniActions.svelte
    │   └── Icon.svelte
    ├── DateTime.svelte
    └── LastEdited.svelte
        └── DateTime.svelte
```

**survey/components/svelte/inline-panel/BooleanResponseRenderer.svelte**

```
└── Icon.svelte
```

**process-diagram/components/process-diagram/svg-elems/Boundary.svelte**

_No dependencies_

**entity-diagrams/components/entity-overlay-diagrams/overlays/assessment/BoxGraph.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/assessments/BoxGraphCell.svelte**

```
└── RatingIndicatorCell.svelte
    └── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/control/BuilderControl.svelte**

```
├── NavTree.svelte
│   └── Icon.svelte
├── RemovalConfirmation.svelte
├── BlockEditor.svelte
└── PersonEditor.svelte
    ├── EntitySearchSelector.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    └── Icon.svelte
```

**assessments/components/bulk-assessment-rating-selector/BulkAssessmentRatingSelector.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
├── Icon.svelte
├── Tooltip.svelte
└── DataCellErrorTooltip.svelte
```

**involvement-kind/components/svelte/BulkInvolvementLoader.svelte**

```
├── Icon.svelte
├── Tooltip.svelte
└── LoaderErrorTooltipContent.svelte
```

**measurable-rating/components/bulk-rating-editor/BulkRatingEditor.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
├── Icon.svelte
├── Tooltip.svelte
└── DataCellErrorTooltip.svelte
```

**system/svelte/bulk-relationships/BulkRelationshipUpload.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
└── SubSection.svelte
```

**measurable-category/components/bulk-taxonomy-editor/BulkTaxonomyEditor.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**legal-entity-relationship-kind/components/bulk-upload/BulkUploadLegalEntityRelationshipsPanel.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
├── SaveUploadReport.svelte
│   └── Icon.svelte
├── ResolvedUploadReport.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── RowErrorTooltip.svelte
│   └── AssessmentErrorTooltip.svelte
├── Icon.svelte
└── Markdown.svelte
```

**common/svelte/calendar-heatmap/CalendarHeatmap.svelte**

```
├── Month.svelte
│   ├── Day.svelte
│   └── Weeks.svelte
└── CalendarHeatmapControlPanel.svelte
    ├── DatePicker.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**common/svelte/calendar-heatmap/CalendarHeatmapControlPanel.svelte**

```
├── DatePicker.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/callout/Callout.svelte**

_No dependencies_

**playpen/3/builder/CalloutBox.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/callout/CalloutCreatePanel.svelte**

```
├── Icon.svelte
└── ColorPicker.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/callout/CalloutDeletePanel.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/callout/CalloutList.svelte**

```
├── Icon.svelte
├── Markdown.svelte
├── NoData.svelte
├── CalloutCreatePanel.svelte
│   ├── Icon.svelte
│   └── ColorPicker.svelte
└── CalloutDeletePanel.svelte
```

**data-flow/components/svelte/Categories.svelte**

_No dependencies_

**entity-diagrams/components/entity-overlay-diagrams/CellContent.svelte**

_No dependencies_

**report-grid/components/svelte/pickers/ChangeInitiativeFieldPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
└── NoData.svelte
```

**common/svelte/info-panels/ChangeInitiativeInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── KeyInvolvementInfoPanel.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── KeyAssessmentInfoPanel.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**system/svelte/analytics-dashboard/ChangeLogsChartView.svelte**

```
├── SubSection.svelte
├── LineChart.svelte
│   ├── LoadingPlaceholder.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
└── DropdownPicker.svelte
    └── Icon.svelte
```

**physical-flows/svelte/Check.svelte**

```
└── Icon.svelte
```

**playpen/1/Child.svelte**

_No dependencies_

**data-flow/components/svelte/ClientContextPanel.svelte**

```
├── Icon.svelte
├── EntityInfoPanel.svelte
│   ├── ApplicationInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── ChangeInitiativeInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── MeasurableInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── ActorInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── DataTypeInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── KeyAssessmentInfoPanel.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   ├── LogicalDataFlowInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   └── KeyAssessmentInfoPanel.svelte
│   ├── SurveyInstanceInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── DateTime.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   └── DatePicker.svelte
│   │       └── Icon.svelte
│   ├── PersonInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── Icon.svelte
│   └── EndUserApplicationInfoPanel.svelte
│       └── DescriptionFade.svelte
│           ├── Markdown.svelte
│           └── Icon.svelte
└── NoData.svelte
```

**data-flow/components/svelte/Clients.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/CloneDiagramSubPanel.svelte**

```
├── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**physical-flows/svelte/ClonePhysicalFlowPanel.svelte**

```
└── SearchInput.svelte
    └── Icon.svelte
```

**system/svelte/ratings-schemes/ColorPicker.svelte**

_No dependencies_

**system/svelte/color-scale/ColorScale.svelte**

```
├── ColorPicker.svelte
├── PageHeader.svelte
│   └── Icon.svelte
└── ViewLink.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/ColumnDefinitionEditPanel.svelte**

```
├── EntitySelector.svelte
│   ├── Icon.svelte
│   └── EntityPicker.svelte
│       ├── InvolvementPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   └── Icon.svelte
│       ├── CostKindPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   └── Icon.svelte
│       ├── SurveyQuestionPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   ├── Icon.svelte
│       │   └── Toggle.svelte
│       │       └── Icon.svelte
│       ├── AssessmentDefinitionPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   └── Icon.svelte
│       ├── PersonPicker.svelte
│       │   ├── Icon.svelte
│       │   └── EntitySearchSelector.svelte
│       │       └── EntityLabel.svelte
│       │           └── EntityIcon.svelte
│       │               └── Icon.svelte
│       ├── SurveyInstanceFieldPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   ├── Icon.svelte
│       │   ├── NoData.svelte
│       │   └── Toggle.svelte
│       │       └── Icon.svelte
│       ├── ApplicationFieldPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   ├── Icon.svelte
│       │   └── NoData.svelte
│       ├── ChangeInitiativeFieldPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   ├── Icon.svelte
│       │   └── NoData.svelte
│       ├── AppGroupPicker.svelte
│       │   ├── Icon.svelte
│       │   └── EntitySearchSelector.svelte
│       │       └── EntityLabel.svelte
│       │           └── EntityIcon.svelte
│       │               └── Icon.svelte
│       ├── DataTypePicker.svelte
│       │   └── DataTypeTreeSelector.svelte
│       │       ├── DataTypeTreeNode.svelte
│       │       │   ├── Icon.svelte
│       │       │   ├── RatingCharacteristicsDecorator.svelte
│       │       │   │   └── FlowRatingCell.svelte
│       │       │   ├── UsageCharacteristicsDecorator.svelte
│       │       │   │   └── Icon.svelte
│       │       │   ├── DataTypeNodeTooltipContent.svelte
│       │       │   │   ├── Icon.svelte
│       │       │   │   ├── RatingIndicatorCell.svelte
│       │       │   │   │   └── Icon.svelte
│       │       │   │   ├── DescriptionFade.svelte
│       │       │   │   │   ├── Markdown.svelte
│       │       │   │   │   └── Icon.svelte
│       │       │   │   ├── NoData.svelte
│       │       │   │   └── Markdown.svelte
│       │       │   └── Tooltip.svelte
│       │       └── SearchInput.svelte
│       │           └── Icon.svelte
│       ├── AttestationPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   └── Icon.svelte
│       ├── OrgUnitFieldPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   ├── Icon.svelte
│       │   └── NoData.svelte
│       ├── TagPicker.svelte
│       │   ├── Icon.svelte
│       │   └── NoData.svelte
│       ├── AliasPicker.svelte
│       │   ├── Icon.svelte
│       │   └── NoData.svelte
│       ├── ComplexityKindPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   └── Icon.svelte
│       ├── MeasurableCategoryPicker.svelte
│       │   ├── Grid.svelte
│       │   │   └── Icon.svelte
│       │   ├── Icon.svelte
│       │   └── MeasurableTreeSelector.svelte
│       │       ├── MeasurableTreeNode.svelte
│       │       │   └── Icon.svelte
│       │       └── SearchInput.svelte
│       │           └── Icon.svelte
│       ├── EntityStatisticPicker.svelte
│       │   └── EntityStatisticTreeSelector.svelte
│       │       ├── SearchInput.svelte
│       │       │   └── Icon.svelte
│       │       └── StatisticTreeNode.svelte
│       │           └── Icon.svelte
│       └── MeasurablePicker.svelte
│           ├── Icon.svelte
│           ├── MeasurableTreeSelector.svelte
│           │   ├── MeasurableTreeNode.svelte
│           │   │   └── Icon.svelte
│           │   └── SearchInput.svelte
│           │       └── Icon.svelte
│           ├── Grid.svelte
│           │   └── Icon.svelte
│           └── NoData.svelte
├── ReportGridColumnSummary.svelte
│   ├── NoData.svelte
│   └── Icon.svelte
├── Icon.svelte
├── FixedColumnDetailsEditor.svelte
│   ├── DropdownPicker.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   └── ColumnDefinitionHeader.svelte
│       └── DescriptionFade.svelte
│           ├── Markdown.svelte
│           └── Icon.svelte
├── DerivedColumnDetailsEditor.svelte
│   ├── Icon.svelte
│   ├── ColumnDefinitionHeader.svelte
│   │   └── DescriptionFade.svelte
│   │       ├── Markdown.svelte
│   │       └── Icon.svelte
│   └── Markdown.svelte
├── NoData.svelte
├── ColumnRemovalConfirmation.svelte
└── Markdown.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/ColumnDefinitionHeader.svelte**

```
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/ColumnRemovalConfirmation.svelte**

_No dependencies_

**flow-classification-rule/components/svelte/CompanionAppRulesPanel.svelte**

```
├── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── NoData.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**flow-classification-rule/components/svelte/CompanionDataTypeRulesPanel.svelte**

```
├── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── NoData.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/complexities/ComplexityAverageCell.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**common/svelte/entity-pickers/ComplexityKindPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/complexities/ComplexityOverlayCell.svelte**

```
├── ComplexityTotalCell.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
└── ComplexityAverageCell.svelte
    └── EntityLink.svelte
        ├── ViewLink.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/complexities/ComplexityTotalCell.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/complexities/ComplexityWidgetParameters.svelte**

```
└── ComplexityKindPicker.svelte
    ├── Grid.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**widgets/Component.svelte**

_No dependencies_

**process-diagram/components/process-diagram/svg-elems/Connections.svelte**

_No dependencies_

**process-diagram/components/process-diagram/context-panels/ConnectionsSubContextPanel.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/ContextPanel.svelte**

```
├── NodePanel.svelte
│   ├── AddLogicalFlowSubPanel.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── AddAnnotationSubPanel.svelte
│   └── Icon.svelte
├── DefaultPanel.svelte
│   ├── AddNodeSubPanel.svelte
│   │   ├── EntitySearchSelector.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── FlowPanel.svelte
│   ├── AddAnnotationSubPanel.svelte
│   ├── AddPhysicalFlowSubPanel.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── AnnotationPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── AddAnnotationSubPanel.svelte
├── Icon.svelte
├── OverlayGroupsPanel.svelte
│   ├── Icon.svelte
│   ├── AddOverlayGroupEntrySubPanel.svelte
│   │   ├── GroupSelectorPanel.svelte
│   │   │   ├── EntitySearchSelector.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   └── MeasurableAlignmentTreeSelector.svelte
│   │   │       ├── SearchInput.svelte
│   │   │       │   └── Icon.svelte
│   │   │       └── MeasurableAlignmentTreeNode.svelte
│   │   │           └── Icon.svelte
│   │   ├── EntitySearchSelector.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── EditOverlayIconSubPanel.svelte
│   │       ├── Icon.svelte
│   │       ├── ColorPicker.svelte
│   │       ├── OverlayGlyph.svelte
│   │       ├── SymbolPicker.svelte
│   │       └── EntityLink.svelte
│   │           ├── ViewLink.svelte
│   │           └── EntityLabel.svelte
│   │               └── EntityIcon.svelte
│   │                   └── Icon.svelte
│   ├── OverlayGlyph.svelte
│   ├── EditOverlayIconSubPanel.svelte
│   │   ├── Icon.svelte
│   │   ├── ColorPicker.svelte
│   │   ├── OverlayGlyph.svelte
│   │   ├── SymbolPicker.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── CreateNewOverlayGroupPanel.svelte
│   │   └── Icon.svelte
│   ├── EntityLabel.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   └── ImportOverlayGroupSubPanel.svelte
│       └── EntitySearchSelector.svelte
│           └── EntityLabel.svelte
│               └── EntityIcon.svelte
│                   └── Icon.svelte
├── EditFlowDiagramOverviewPanel.svelte
│   └── Icon.svelte
├── VisibilityToggles.svelte
├── RelatedEntitiesPanel.svelte
│   ├── RelatedEntitiesViewTable.svelte
│   │   ├── Icon.svelte
│   │   ├── RelatedEntityAddNodesPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── RelatedEntityAddFlowsPanel.svelte
│   │   │   └── Icon.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── AddRelatedMeasurableSubPanel.svelte
│   │   ├── Icon.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── AddRelatedChangeInitiativeSubPanel.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   └── AddRelatedDataTypeSubPanel.svelte
│       └── EntitySearchSelector.svelte
│           └── EntityLabel.svelte
│               └── EntityIcon.svelte
│                   └── Icon.svelte
├── CloneDiagramSubPanel.svelte
│   ├── Icon.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── RemoveDiagramSubPanel.svelte
│   └── Icon.svelte
└── Markdown.svelte
```

**survey/components/svelte/inline-panel/CopySurveyResponsesPanel.svelte**

```
├── NoData.svelte
├── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**common/svelte/entity-pickers/CostKindPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**logical-flow/components/edit/svelte/CounterpartPicker.svelte**

```
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/CreateNewOverlayGroupPanel.svelte**

```
└── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/presets/CreatePresetPanel.svelte**

```
└── Icon.svelte
```

**system/svelte/settings/CreateSettingPanel.svelte**

_No dependencies_

**technology/svelte/custom-environment-panel/CustomEnvironmentPanel.svelte**

```
├── Icon.svelte
├── UsagePanel.svelte
│   ├── UsageEdit.svelte
│   │   ├── EntitySearchSelector.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── Icon.svelte
│   │   ├── UsageTree.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── MiniActions.svelte
│   │   │       └── Icon.svelte
│   │   ├── SearchInput.svelte
│   │   │   └── Icon.svelte
│   │   └── MiniActions.svelte
│   │       └── Icon.svelte
│   ├── EnvironmentRemovalConfirmation.svelte
│   ├── UsageTree.svelte
│   │   ├── Icon.svelte
│   │   └── MiniActions.svelte
│   │       └── Icon.svelte
│   ├── UsageTable.svelte
│   │   ├── Icon.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── MiniActions.svelte
│   │   └── Icon.svelte
│   └── UsageAssetInfo.svelte
│       ├── EntityLink.svelte
│       │   ├── ViewLink.svelte
│       │   └── EntityLabel.svelte
│       │       └── EntityIcon.svelte
│       │           └── Icon.svelte
│       └── Icon.svelte
├── EnvironmentRegistration.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**aggregate-overlay-diagram/components/context-panel/CustomiseOverlayPanel.svelte**

```
├── WidgetSelector.svelte
│   └── Icon.svelte
├── CreatePresetPanel.svelte
│   └── Icon.svelte
├── PresetSelector.svelte
│   ├── NoData.svelte
│   └── Markdown.svelte
├── Icon.svelte
├── NoData.svelte
└── FilterPanel.svelte
    ├── FilterSelectorPanel.svelte
    │   ├── AssessmentRatingPicker.svelte
    │   │   ├── AssessmentDefinitionPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── RatingPicker.svelte
    │   │   │   ├── RatingIndicatorCell.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   └── Markdown.svelte
    │   └── Icon.svelte
    ├── FilterList.svelte
    │   ├── RatingIndicatorCell.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**assessments/components/bulk-assessment-rating-selector/DataCellErrorTooltip.svelte**

_No dependencies_

**measurable-rating/components/bulk-rating-editor/DataCellErrorTooltip.svelte**

_No dependencies_

**common/svelte/DataExtractLink.svelte**

```
└── Icon.svelte
```

**common/svelte/DataTypeDecoratorNodeContent.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── Icon.svelte
├── Tooltip.svelte
└── DataTypeDecoratorTreeTooltip.svelte
    ├── DescriptionFade.svelte
    │   ├── Markdown.svelte
    │   └── Icon.svelte
    ├── Icon.svelte
    ├── RatingIndicatorCell.svelte
    │   └── Icon.svelte
    ├── LastEdited.svelte
    │   └── DateTime.svelte
    └── DataTypeDecoratorViewTable.svelte
        ├── EntityLink.svelte
        │   ├── ViewLink.svelte
        │   └── EntityLabel.svelte
        │       └── EntityIcon.svelte
        │           └── Icon.svelte
        ├── RatingIndicatorCell.svelte
        │   └── Icon.svelte
        ├── LastEdited.svelte
        │   └── DateTime.svelte
        └── Icon.svelte
```

**data-types/components/data-type-decorator-section/DataTypeDecoratorSection.svelte**

```
├── DataTypeOverviewPanel.svelte
│   ├── DataTypeTreeSelector.svelte
│   │   ├── DataTypeTreeNode.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── RatingCharacteristicsDecorator.svelte
│   │   │   │   └── FlowRatingCell.svelte
│   │   │   ├── UsageCharacteristicsDecorator.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── DataTypeNodeTooltipContent.svelte
│   │   │   │   ├── Icon.svelte
│   │   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   │   └── Icon.svelte
│   │   │   │   ├── DescriptionFade.svelte
│   │   │   │   │   ├── Markdown.svelte
│   │   │   │   │   └── Icon.svelte
│   │   │   │   ├── NoData.svelte
│   │   │   │   └── Markdown.svelte
│   │   │   └── Tooltip.svelte
│   │   └── SearchInput.svelte
│   │       └── Icon.svelte
│   ├── Icon.svelte
│   ├── SavingPlaceholder.svelte
│   │   └── Icon.svelte
│   └── SuggestedDataTypeTreeSelector.svelte
│       └── DataTypeTreeSelector.svelte
│           ├── DataTypeTreeNode.svelte
│           │   ├── Icon.svelte
│           │   ├── RatingCharacteristicsDecorator.svelte
│           │   │   └── FlowRatingCell.svelte
│           │   ├── UsageCharacteristicsDecorator.svelte
│           │   │   └── Icon.svelte
│           │   ├── DataTypeNodeTooltipContent.svelte
│           │   │   ├── Icon.svelte
│           │   │   ├── RatingIndicatorCell.svelte
│           │   │   │   └── Icon.svelte
│           │   │   ├── DescriptionFade.svelte
│           │   │   │   ├── Markdown.svelte
│           │   │   │   └── Icon.svelte
│           │   │   ├── NoData.svelte
│           │   │   └── Markdown.svelte
│           │   └── Tooltip.svelte
│           └── SearchInput.svelte
│               └── Icon.svelte
├── DataTypeDetailContextPanel.svelte
│   ├── Icon.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── FlowClassificationRuleViewTable.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── RatingIndicatorCell.svelte
│   │       └── Icon.svelte
│   ├── DataTypeDecoratorViewTable.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   ├── LastEdited.svelte
│   │   │   └── DateTime.svelte
│   │   └── Icon.svelte
│   └── DataTypeViewTable.svelte
│       ├── EntityLink.svelte
│       │   ├── ViewLink.svelte
│       │   └── EntityLabel.svelte
│       │       └── EntityIcon.svelte
│       │           └── Icon.svelte
│       ├── DescriptionFade.svelte
│       │   ├── Markdown.svelte
│       │   └── Icon.svelte
│       └── Icon.svelte
├── Toggle.svelte
│   └── Icon.svelte
└── DataTypeDecoratorViewGrid.svelte
    ├── NoData.svelte
    ├── SearchInput.svelte
    │   └── Icon.svelte
    └── FlowDecoratorFilters.svelte
        ├── Icon.svelte
        ├── AssessmentFilters.svelte
        │   ├── RatingIndicatorCell.svelte
        │   │   └── Icon.svelte
        │   ├── NoData.svelte
        │   └── EntityIcon.svelte
        │       └── Icon.svelte
        └── FlowClassificationFilters.svelte
            ├── RatingIndicatorCell.svelte
            │   └── Icon.svelte
            └── NoData.svelte
```

**common/svelte/DataTypeDecoratorTreeNode.svelte**

```
├── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── DataTypeDecoratorNodeContent.svelte
    ├── RatingIndicatorCell.svelte
    │   └── Icon.svelte
    ├── Icon.svelte
    ├── Tooltip.svelte
    └── DataTypeDecoratorTreeTooltip.svelte
        ├── DescriptionFade.svelte
        │   ├── Markdown.svelte
        │   └── Icon.svelte
        ├── Icon.svelte
        ├── RatingIndicatorCell.svelte
        │   └── Icon.svelte
        ├── LastEdited.svelte
        │   └── DateTime.svelte
        └── DataTypeDecoratorViewTable.svelte
            ├── EntityLink.svelte
            │   ├── ViewLink.svelte
            │   └── EntityLabel.svelte
            │       └── EntityIcon.svelte
            │           └── Icon.svelte
            ├── RatingIndicatorCell.svelte
            │   └── Icon.svelte
            ├── LastEdited.svelte
            │   └── DateTime.svelte
            └── Icon.svelte
```

**common/svelte/DataTypeDecoratorTreeTooltip.svelte**

```
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
├── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
└── DataTypeDecoratorViewTable.svelte
    ├── EntityLink.svelte
    │   ├── ViewLink.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    ├── RatingIndicatorCell.svelte
    │   └── Icon.svelte
    ├── LastEdited.svelte
    │   └── DateTime.svelte
    └── Icon.svelte
```

**common/svelte/DataTypeDecoratorTreeView.svelte**

```
├── DataTypeDecoratorTreeNode.svelte
│   ├── Icon.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── DataTypeDecoratorNodeContent.svelte
│       ├── RatingIndicatorCell.svelte
│       │   └── Icon.svelte
│       ├── Icon.svelte
│       ├── Tooltip.svelte
│       └── DataTypeDecoratorTreeTooltip.svelte
│           ├── DescriptionFade.svelte
│           │   ├── Markdown.svelte
│           │   └── Icon.svelte
│           ├── Icon.svelte
│           ├── RatingIndicatorCell.svelte
│           │   └── Icon.svelte
│           ├── LastEdited.svelte
│           │   └── DateTime.svelte
│           └── DataTypeDecoratorViewTable.svelte
│               ├── EntityLink.svelte
│               │   ├── ViewLink.svelte
│               │   └── EntityLabel.svelte
│               │       └── EntityIcon.svelte
│               │           └── Icon.svelte
│               ├── RatingIndicatorCell.svelte
│               │   └── Icon.svelte
│               ├── LastEdited.svelte
│               │   └── DateTime.svelte
│               └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**data-types/components/data-type-decorator-section/DataTypeDecoratorViewGrid.svelte**

```
├── NoData.svelte
├── SearchInput.svelte
│   └── Icon.svelte
└── FlowDecoratorFilters.svelte
    ├── Icon.svelte
    ├── AssessmentFilters.svelte
    │   ├── RatingIndicatorCell.svelte
    │   │   └── Icon.svelte
    │   ├── NoData.svelte
    │   └── EntityIcon.svelte
    │       └── Icon.svelte
    └── FlowClassificationFilters.svelte
        ├── RatingIndicatorCell.svelte
        │   └── Icon.svelte
        └── NoData.svelte
```

**data-types/components/data-type-decorator-section/context-panel/DataTypeDecoratorViewTable.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
└── Icon.svelte
```

**data-types/components/data-type-decorator-section/context-panel/DataTypeDetailContextPanel.svelte**

```
├── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── FlowClassificationRuleViewTable.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── RatingIndicatorCell.svelte
│       └── Icon.svelte
├── DataTypeDecoratorViewTable.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── LastEdited.svelte
│   │   └── DateTime.svelte
│   └── Icon.svelte
└── DataTypeViewTable.svelte
    ├── EntityLink.svelte
    │   ├── ViewLink.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    ├── DescriptionFade.svelte
    │   ├── Markdown.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**data-flow/components/svelte/DataTypeDetailTable.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── Icon.svelte
└── NoData.svelte
```

**data-flow/components/svelte/flow-detail-tab/filters/DataTypeFilters.svelte**

```
└── DataTypeTreeSelector.svelte
    ├── DataTypeTreeNode.svelte
    │   ├── Icon.svelte
    │   ├── RatingCharacteristicsDecorator.svelte
    │   │   └── FlowRatingCell.svelte
    │   ├── UsageCharacteristicsDecorator.svelte
    │   │   └── Icon.svelte
    │   ├── DataTypeNodeTooltipContent.svelte
    │   │   ├── Icon.svelte
    │   │   ├── RatingIndicatorCell.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   └── Markdown.svelte
    │   └── Tooltip.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

**common/svelte/info-panels/DataTypeInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── KeyInvolvementInfoPanel.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── KeyAssessmentInfoPanel.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**logical-flow/components/data-type-info-panel/DataTypeInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**data-types/components/maintain/DataTypeMaintain.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
├── DataTypeTreeSelector.svelte
│   ├── DataTypeTreeNode.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingCharacteristicsDecorator.svelte
│   │   │   └── FlowRatingCell.svelte
│   │   ├── UsageCharacteristicsDecorator.svelte
│   │   │   └── Icon.svelte
│   │   ├── DataTypeNodeTooltipContent.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── DescriptionFade.svelte
│   │   │   │   ├── Markdown.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── NoData.svelte
│   │   │   └── Markdown.svelte
│   │   └── Tooltip.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── SubSection.svelte
└── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/DataTypeMiniTable.svelte**

```
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
├── Toggle.svelte
│   └── Icon.svelte
├── FlowRatingCell.svelte
└── RatingIndicatorCell.svelte
    └── Icon.svelte
```

**system/svelte/nav-aid-builder/DataTypeNavAidBuilder.svelte**

```
└── TaxonomyNavAidBuilder.svelte
    └── ColorPicker.svelte
```

**common/svelte/DataTypeNodeTooltipContent.svelte**

```
├── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
├── NoData.svelte
└── Markdown.svelte
```

**data-types/components/data-type-decorator-section/DataTypeOverviewPanel.svelte**

```
├── DataTypeTreeSelector.svelte
│   ├── DataTypeTreeNode.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingCharacteristicsDecorator.svelte
│   │   │   └── FlowRatingCell.svelte
│   │   ├── UsageCharacteristicsDecorator.svelte
│   │   │   └── Icon.svelte
│   │   ├── DataTypeNodeTooltipContent.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── DescriptionFade.svelte
│   │   │   │   ├── Markdown.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── NoData.svelte
│   │   │   └── Markdown.svelte
│   │   └── Tooltip.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── Icon.svelte
├── SavingPlaceholder.svelte
│   └── Icon.svelte
└── SuggestedDataTypeTreeSelector.svelte
    └── DataTypeTreeSelector.svelte
        ├── DataTypeTreeNode.svelte
        │   ├── Icon.svelte
        │   ├── RatingCharacteristicsDecorator.svelte
        │   │   └── FlowRatingCell.svelte
        │   ├── UsageCharacteristicsDecorator.svelte
        │   │   └── Icon.svelte
        │   ├── DataTypeNodeTooltipContent.svelte
        │   │   ├── Icon.svelte
        │   │   ├── RatingIndicatorCell.svelte
        │   │   │   └── Icon.svelte
        │   │   ├── DescriptionFade.svelte
        │   │   │   ├── Markdown.svelte
        │   │   │   └── Icon.svelte
        │   │   ├── NoData.svelte
        │   │   └── Markdown.svelte
        │   └── Tooltip.svelte
        └── SearchInput.svelte
            └── Icon.svelte
```

**common/svelte/entity-pickers/DataTypePicker.svelte**

```
└── DataTypeTreeSelector.svelte
    ├── DataTypeTreeNode.svelte
    │   ├── Icon.svelte
    │   ├── RatingCharacteristicsDecorator.svelte
    │   │   └── FlowRatingCell.svelte
    │   ├── UsageCharacteristicsDecorator.svelte
    │   │   └── Icon.svelte
    │   ├── DataTypeNodeTooltipContent.svelte
    │   │   ├── Icon.svelte
    │   │   ├── RatingIndicatorCell.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   └── Markdown.svelte
    │   └── Tooltip.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

**physical-flows/svelte/DataTypeSelectionStep.svelte**

```
├── StepHeader.svelte
│   ├── Check.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── Icon.svelte
├── DataTypeTreeSelector.svelte
│   ├── DataTypeTreeNode.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingCharacteristicsDecorator.svelte
│   │   │   └── FlowRatingCell.svelte
│   │   ├── UsageCharacteristicsDecorator.svelte
│   │   │   └── Icon.svelte
│   │   ├── DataTypeNodeTooltipContent.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── DescriptionFade.svelte
│   │   │   │   ├── Markdown.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── NoData.svelte
│   │   │   └── Markdown.svelte
│   │   └── Tooltip.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
└── NoData.svelte
```

**data-types/components/overview/DataTypeTreeNav.svelte**

```
├── DataTypeTreeNode.svelte
│   ├── Icon.svelte
│   ├── RatingCharacteristicsDecorator.svelte
│   │   └── FlowRatingCell.svelte
│   ├── UsageCharacteristicsDecorator.svelte
│   │   └── Icon.svelte
│   ├── DataTypeNodeTooltipContent.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   ├── NoData.svelte
│   │   └── Markdown.svelte
│   └── Tooltip.svelte
└── SubSection.svelte
```

**common/svelte/DataTypeTreeNode.svelte**

```
├── Icon.svelte
├── RatingCharacteristicsDecorator.svelte
│   └── FlowRatingCell.svelte
├── UsageCharacteristicsDecorator.svelte
│   └── Icon.svelte
├── DataTypeNodeTooltipContent.svelte
│   ├── Icon.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── DescriptionFade.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   └── Markdown.svelte
└── Tooltip.svelte
```

**common/svelte/DataTypeTreeSelector.svelte**

```
├── DataTypeTreeNode.svelte
│   ├── Icon.svelte
│   ├── RatingCharacteristicsDecorator.svelte
│   │   └── FlowRatingCell.svelte
│   ├── UsageCharacteristicsDecorator.svelte
│   │   └── Icon.svelte
│   ├── DataTypeNodeTooltipContent.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   ├── NoData.svelte
│   │   └── Markdown.svelte
│   └── Tooltip.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**data-types/components/data-type-decorator-section/context-panel/DataTypeViewTable.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**common/svelte/DatePicker.svelte**

```
└── Icon.svelte
```

**survey/components/svelte/inline-panel/DateResponseRenderer.svelte**

_No dependencies_

**common/svelte/DateTime.svelte**

_No dependencies_

**common/svelte/calendar-heatmap/Day.svelte**

_No dependencies_

**process-diagram/components/process-diagram/svg-elems/Decision.svelte**

_No dependencies_

**process-diagram/components/process-diagram/context-panels/DecisionContextPanel.svelte**

```
└── ConnectionsSubContextPanel.svelte
```

**data-flow/components/svelte/DefaultContextPanel.svelte**

```
├── Icon.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**process-diagram/components/process-diagram/context-panels/DefaultContextPanel.svelte**

```
├── Icon.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**data-flow/components/svelte/DefaultFilters.svelte**

```
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/DefaultOverlay.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/DefaultOverlayParameters.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/DefaultPanel.svelte**

```
├── AddNodeSubPanel.svelte
│   ├── EntitySearchSelector.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**process-diagram/components/process-diagram/svg-elems/Defs.svelte**

_No dependencies_

**user/svelte/DeleteUserConfirmationPanel.svelte**

```
└── NoData.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/DerivedColumnDetailsEditor.svelte**

```
├── Icon.svelte
├── ColumnDefinitionHeader.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
└── Markdown.svelte
```

**common/svelte/DescriptionFade.svelte**

```
├── Markdown.svelte
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder/DiagramBuilder.svelte**

```
├── DiagramBuildView.svelte
│   └── CellContent.svelte
├── DiagramControls.svelte
│   ├── Icon.svelte
│   ├── DiagramList.svelte
│   │   └── NoData.svelte
│   ├── EditDiagramDetailsPanel.svelte
│   │   └── DropdownPicker.svelte
│   │       └── Icon.svelte
│   ├── UpdateDiagramConfirmationPanel.svelte
│   │   └── NoData.svelte
│   └── Toggle.svelte
│       └── Icon.svelte
├── DiagramTreeSelector.svelte
│   ├── DiagramTreeNode.svelte
│   │   └── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── DiagramView.svelte
│   ├── CellContent.svelte
│   ├── Icon.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── GroupDetailsPanel.svelte
│   └── Icon.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
└── GroupControls.svelte
    ├── EditGroupPanel.svelte
    │   └── ColorPicker.svelte
    ├── EditItemsPanel.svelte
    │   ├── EntityPicker.svelte
    │   │   ├── InvolvementPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── CostKindPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── SurveyQuestionPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── Toggle.svelte
    │   │   │       └── Icon.svelte
    │   │   ├── AssessmentDefinitionPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── PersonPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── EntitySearchSelector.svelte
    │   │   │       └── EntityLabel.svelte
    │   │   │           └── EntityIcon.svelte
    │   │   │               └── Icon.svelte
    │   │   ├── SurveyInstanceFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   ├── NoData.svelte
    │   │   │   └── Toggle.svelte
    │   │   │       └── Icon.svelte
    │   │   ├── ApplicationFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── ChangeInitiativeFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── AppGroupPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── EntitySearchSelector.svelte
    │   │   │       └── EntityLabel.svelte
    │   │   │           └── EntityIcon.svelte
    │   │   │               └── Icon.svelte
    │   │   ├── DataTypePicker.svelte
    │   │   │   └── DataTypeTreeSelector.svelte
    │   │   │       ├── DataTypeTreeNode.svelte
    │   │   │       │   ├── Icon.svelte
    │   │   │       │   ├── RatingCharacteristicsDecorator.svelte
    │   │   │       │   │   └── FlowRatingCell.svelte
    │   │   │       │   ├── UsageCharacteristicsDecorator.svelte
    │   │   │       │   │   └── Icon.svelte
    │   │   │       │   ├── DataTypeNodeTooltipContent.svelte
    │   │   │       │   │   ├── Icon.svelte
    │   │   │       │   │   ├── RatingIndicatorCell.svelte
    │   │   │       │   │   │   └── Icon.svelte
    │   │   │       │   │   ├── DescriptionFade.svelte
    │   │   │       │   │   │   ├── Markdown.svelte
    │   │   │       │   │   │   └── Icon.svelte
    │   │   │       │   │   ├── NoData.svelte
    │   │   │       │   │   └── Markdown.svelte
    │   │   │       │   └── Tooltip.svelte
    │   │   │       └── SearchInput.svelte
    │   │   │           └── Icon.svelte
    │   │   ├── AttestationPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── OrgUnitFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── TagPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── AliasPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── ComplexityKindPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── MeasurableCategoryPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── MeasurableTreeSelector.svelte
    │   │   │       ├── MeasurableTreeNode.svelte
    │   │   │       │   └── Icon.svelte
    │   │   │       └── SearchInput.svelte
    │   │   │           └── Icon.svelte
    │   │   ├── EntityStatisticPicker.svelte
    │   │   │   └── EntityStatisticTreeSelector.svelte
    │   │   │       ├── SearchInput.svelte
    │   │   │       │   └── Icon.svelte
    │   │   │       └── StatisticTreeNode.svelte
    │   │   │           └── Icon.svelte
    │   │   └── MeasurablePicker.svelte
    │   │       ├── Icon.svelte
    │   │       ├── MeasurableTreeSelector.svelte
    │   │       │   ├── MeasurableTreeNode.svelte
    │   │       │   │   └── Icon.svelte
    │   │       │   └── SearchInput.svelte
    │   │       │       └── Icon.svelte
    │   │       ├── Grid.svelte
    │   │       │   └── Icon.svelte
    │   │       └── NoData.svelte
    │   ├── DropdownPicker.svelte
    │   │   └── Icon.svelte
    │   ├── ColorPicker.svelte
    │   └── Icon.svelte
    ├── GroupDetailsPanel.svelte
    │   └── Icon.svelte
    ├── AddItemsPanel.svelte
    │   ├── EntityPicker.svelte
    │   │   ├── InvolvementPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── CostKindPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── SurveyQuestionPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── Toggle.svelte
    │   │   │       └── Icon.svelte
    │   │   ├── AssessmentDefinitionPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── PersonPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── EntitySearchSelector.svelte
    │   │   │       └── EntityLabel.svelte
    │   │   │           └── EntityIcon.svelte
    │   │   │               └── Icon.svelte
    │   │   ├── SurveyInstanceFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   ├── NoData.svelte
    │   │   │   └── Toggle.svelte
    │   │   │       └── Icon.svelte
    │   │   ├── ApplicationFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── ChangeInitiativeFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── AppGroupPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── EntitySearchSelector.svelte
    │   │   │       └── EntityLabel.svelte
    │   │   │           └── EntityIcon.svelte
    │   │   │               └── Icon.svelte
    │   │   ├── DataTypePicker.svelte
    │   │   │   └── DataTypeTreeSelector.svelte
    │   │   │       ├── DataTypeTreeNode.svelte
    │   │   │       │   ├── Icon.svelte
    │   │   │       │   ├── RatingCharacteristicsDecorator.svelte
    │   │   │       │   │   └── FlowRatingCell.svelte
    │   │   │       │   ├── UsageCharacteristicsDecorator.svelte
    │   │   │       │   │   └── Icon.svelte
    │   │   │       │   ├── DataTypeNodeTooltipContent.svelte
    │   │   │       │   │   ├── Icon.svelte
    │   │   │       │   │   ├── RatingIndicatorCell.svelte
    │   │   │       │   │   │   └── Icon.svelte
    │   │   │       │   │   ├── DescriptionFade.svelte
    │   │   │       │   │   │   ├── Markdown.svelte
    │   │   │       │   │   │   └── Icon.svelte
    │   │   │       │   │   ├── NoData.svelte
    │   │   │       │   │   └── Markdown.svelte
    │   │   │       │   └── Tooltip.svelte
    │   │   │       └── SearchInput.svelte
    │   │   │           └── Icon.svelte
    │   │   ├── AttestationPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── OrgUnitFieldPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── TagPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── AliasPicker.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── NoData.svelte
    │   │   ├── ComplexityKindPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── MeasurableCategoryPicker.svelte
    │   │   │   ├── Grid.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   └── MeasurableTreeSelector.svelte
    │   │   │       ├── MeasurableTreeNode.svelte
    │   │   │       │   └── Icon.svelte
    │   │   │       └── SearchInput.svelte
    │   │   │           └── Icon.svelte
    │   │   ├── EntityStatisticPicker.svelte
    │   │   │   └── EntityStatisticTreeSelector.svelte
    │   │   │       ├── SearchInput.svelte
    │   │   │       │   └── Icon.svelte
    │   │   │       └── StatisticTreeNode.svelte
    │   │   │           └── Icon.svelte
    │   │   └── MeasurablePicker.svelte
    │   │       ├── Icon.svelte
    │   │       ├── MeasurableTreeSelector.svelte
    │   │       │   ├── MeasurableTreeNode.svelte
    │   │       │   │   └── Icon.svelte
    │   │       │   └── SearchInput.svelte
    │   │       │       └── Icon.svelte
    │   │       ├── Grid.svelte
    │   │       │   └── Icon.svelte
    │   │       └── NoData.svelte
    │   ├── DropdownPicker.svelte
    │   │   └── Icon.svelte
    │   ├── ColorPicker.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**playpen/3/builder/DiagramBuilderControls.svelte**

_No dependencies_

**entity-diagrams/components/entity-overlay-diagrams/builder/DiagramBuildView.svelte**

```
└── CellContent.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/context-panel/DiagramContextPanel.svelte**

```
├── Icon.svelte
├── OverlayDiagramPicker.svelte
│   ├── Icon.svelte
│   └── Toggle.svelte
│       └── Icon.svelte
└── SelectedGroupPanel.svelte
    ├── EntityLink.svelte
    │   ├── ViewLink.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    ├── CellContent.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder-controls/DiagramControls.svelte**

```
├── Icon.svelte
├── DiagramList.svelte
│   └── NoData.svelte
├── EditDiagramDetailsPanel.svelte
│   └── DropdownPicker.svelte
│       └── Icon.svelte
├── UpdateDiagramConfirmationPanel.svelte
│   └── NoData.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**aggregate-overlay-diagram/components/instance-selector/DiagramInstanceSelector.svelte**

```
├── LastEdited.svelte
│   └── DateTime.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── InstanceCreatePanel.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/interact-view/DiagramInteractView.svelte**

```
├── CellContent.svelte
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/DiagramList.svelte**

```
└── NoData.svelte
```

**aggregate-overlay-diagram/components/diagram-selector/DiagramSelector.svelte**

```
├── LastEdited.svelte
│   └── DateTime.svelte
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder/DiagramTreeNode.svelte**

```
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder/DiagramTreeSelector.svelte**

```
├── DiagramTreeNode.svelte
│   └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/static-view/DiagramView.svelte**

```
├── CellContent.svelte
├── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**survey/components/svelte/inline-panel/DropdownMultiResponseRenderer.svelte**

_No dependencies_

**common/svelte/DropdownPicker.svelte**

```
└── Icon.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/DropdownPicker.svelte**

```
└── Icon.svelte
```

**assessments/components/rating-editor/EditableRatingValue.svelte**

```
├── Icon.svelte
├── SavingPlaceholder.svelte
│   └── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── RatingItemDropdownPicker.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder/EditDiagramDetailsPanel.svelte**

```
└── DropdownPicker.svelte
    └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/EditFlowDiagramOverviewPanel.svelte**

```
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder-controls/EditGroupPanel.svelte**

```
└── ColorPicker.svelte
```

**involvement-kind/components/svelte/EditInvolvementKindPanel.svelte**

```
├── Icon.svelte
└── EntityIcon.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder-controls/EditItemsPanel.svelte**

```
├── EntityPicker.svelte
│   ├── InvolvementPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── CostKindPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── SurveyQuestionPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── Toggle.svelte
│   │       └── Icon.svelte
│   ├── AssessmentDefinitionPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── PersonPicker.svelte
│   │   ├── Icon.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── SurveyInstanceFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   ├── NoData.svelte
│   │   └── Toggle.svelte
│   │       └── Icon.svelte
│   ├── ApplicationFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── ChangeInitiativeFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── AppGroupPicker.svelte
│   │   ├── Icon.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── DataTypePicker.svelte
│   │   └── DataTypeTreeSelector.svelte
│   │       ├── DataTypeTreeNode.svelte
│   │       │   ├── Icon.svelte
│   │       │   ├── RatingCharacteristicsDecorator.svelte
│   │       │   │   └── FlowRatingCell.svelte
│   │       │   ├── UsageCharacteristicsDecorator.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── DataTypeNodeTooltipContent.svelte
│   │       │   │   ├── Icon.svelte
│   │       │   │   ├── RatingIndicatorCell.svelte
│   │       │   │   │   └── Icon.svelte
│   │       │   │   ├── DescriptionFade.svelte
│   │       │   │   │   ├── Markdown.svelte
│   │       │   │   │   └── Icon.svelte
│   │       │   │   ├── NoData.svelte
│   │       │   │   └── Markdown.svelte
│   │       │   └── Tooltip.svelte
│   │       └── SearchInput.svelte
│   │           └── Icon.svelte
│   ├── AttestationPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── OrgUnitFieldPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── TagPicker.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── AliasPicker.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── ComplexityKindPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── MeasurableCategoryPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── MeasurableTreeSelector.svelte
│   │       ├── MeasurableTreeNode.svelte
│   │       │   └── Icon.svelte
│   │       └── SearchInput.svelte
│   │           └── Icon.svelte
│   ├── EntityStatisticPicker.svelte
│   │   └── EntityStatisticTreeSelector.svelte
│   │       ├── SearchInput.svelte
│   │       │   └── Icon.svelte
│   │       └── StatisticTreeNode.svelte
│   │           └── Icon.svelte
│   └── MeasurablePicker.svelte
│       ├── Icon.svelte
│       ├── MeasurableTreeSelector.svelte
│       │   ├── MeasurableTreeNode.svelte
│       │   │   └── Icon.svelte
│       │   └── SearchInput.svelte
│       │       └── Icon.svelte
│       ├── Grid.svelte
│       │   └── Icon.svelte
│       └── NoData.svelte
├── DropdownPicker.svelte
│   └── Icon.svelte
├── ColorPicker.svelte
└── Icon.svelte
```

**system/svelte/licences/EditLicencePanel.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/EditOverlayIconSubPanel.svelte**

```
├── Icon.svelte
├── ColorPicker.svelte
├── OverlayGlyph.svelte
├── SymbolPicker.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**common/svelte/info-panels/EndUserApplicationInfoPanel.svelte**

```
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**end-user-apps/svelte/EndUserApplicationOverview.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── AssessmentFavouritesList.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── AssessmentRatingTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   ├── Icon.svelte
│   │   └── LastEdited.svelte
│   │       └── DateTime.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
├── SubSection.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
├── Icon.svelte
└── AliasControl.svelte
    └── TagsInput.svelte
```

**playpen/3/builder/EntityCell.svelte**

```
├── StatisticsBox.svelte
└── CalloutBox.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/EntityDiagramPanel.svelte**

```
├── DiagramContextPanel.svelte
│   ├── Icon.svelte
│   ├── OverlayDiagramPicker.svelte
│   │   ├── Icon.svelte
│   │   └── Toggle.svelte
│   │       └── Icon.svelte
│   └── SelectedGroupPanel.svelte
│       ├── EntityLink.svelte
│       │   ├── ViewLink.svelte
│       │   └── EntityLabel.svelte
│       │       └── EntityIcon.svelte
│       │           └── Icon.svelte
│       ├── CellContent.svelte
│       └── Icon.svelte
├── DiagramInteractView.svelte
│   ├── CellContent.svelte
│   └── Icon.svelte
└── DiagramList.svelte
    └── NoData.svelte
```

**playpen/3/builder/EntityGroupBox.svelte**

```
├── GroupRow.svelte
│   └── EntityCell.svelte
│       ├── StatisticsBox.svelte
│       └── CalloutBox.svelte
└── CalloutBox.svelte
```

**common/svelte/EntityIcon.svelte**

```
└── Icon.svelte
```

**common/svelte/info-panels/EntityInfoPanel.svelte**

```
├── ApplicationInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── KeyInvolvementInfoPanel.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── KeyAssessmentInfoPanel.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── ChangeInitiativeInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── KeyInvolvementInfoPanel.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── KeyAssessmentInfoPanel.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── MeasurableInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── KeyInvolvementInfoPanel.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── KeyAssessmentInfoPanel.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── ActorInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── KeyInvolvementInfoPanel.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── KeyAssessmentInfoPanel.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── DataTypeInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── KeyInvolvementInfoPanel.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── KeyAssessmentInfoPanel.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── LogicalDataFlowInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── KeyInvolvementInfoPanel.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   └── KeyAssessmentInfoPanel.svelte
├── SurveyInstanceInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── DateTime.svelte
│   ├── DescriptionFade.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── DatePicker.svelte
│       └── Icon.svelte
├── PersonInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── Icon.svelte
└── EndUserApplicationInfoPanel.svelte
    └── DescriptionFade.svelte
        ├── Markdown.svelte
        └── Icon.svelte
```

**common/svelte/EntityLabel.svelte**

```
└── EntityIcon.svelte
    └── Icon.svelte
```

**common/svelte/EntityLink.svelte**

```
├── ViewLink.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**entity-named-note/components/section/EntityNamedNoteAddPanel.svelte**

```
├── Icon.svelte
├── Markdown.svelte
└── EntityNamedNoteEditPanel.svelte
    ├── Icon.svelte
    └── Markdown.svelte
```

**entity-named-note/components/section/EntityNamedNoteEditPanel.svelte**

```
├── Icon.svelte
└── Markdown.svelte
```

**entity-named-note/components/section/EntityNamedNoteRemovalPanel.svelte**

```
├── Icon.svelte
└── Markdown.svelte
```

**entity-named-note/components/section/EntityNamedNotesSection.svelte**

```
├── NoData.svelte
├── Markdown.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── Icon.svelte
├── EntityNamedNoteRemovalPanel.svelte
│   ├── Icon.svelte
│   └── Markdown.svelte
├── EntityNamedNoteEditPanel.svelte
│   ├── Icon.svelte
│   └── Markdown.svelte
└── EntityNamedNoteAddPanel.svelte
    ├── Icon.svelte
    ├── Markdown.svelte
    └── EntityNamedNoteEditPanel.svelte
        ├── Icon.svelte
        └── Markdown.svelte
```

**report-grid/components/svelte/pickers/EntityPicker.svelte**

```
├── InvolvementPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── CostKindPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── SurveyQuestionPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   └── Toggle.svelte
│       └── Icon.svelte
├── AssessmentDefinitionPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── PersonPicker.svelte
│   ├── Icon.svelte
│   └── EntitySearchSelector.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── SurveyInstanceFieldPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   ├── NoData.svelte
│   └── Toggle.svelte
│       └── Icon.svelte
├── ApplicationFieldPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
├── ChangeInitiativeFieldPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
├── AppGroupPicker.svelte
│   ├── Icon.svelte
│   └── EntitySearchSelector.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── DataTypePicker.svelte
│   └── DataTypeTreeSelector.svelte
│       ├── DataTypeTreeNode.svelte
│       │   ├── Icon.svelte
│       │   ├── RatingCharacteristicsDecorator.svelte
│       │   │   └── FlowRatingCell.svelte
│       │   ├── UsageCharacteristicsDecorator.svelte
│       │   │   └── Icon.svelte
│       │   ├── DataTypeNodeTooltipContent.svelte
│       │   │   ├── Icon.svelte
│       │   │   ├── RatingIndicatorCell.svelte
│       │   │   │   └── Icon.svelte
│       │   │   ├── DescriptionFade.svelte
│       │   │   │   ├── Markdown.svelte
│       │   │   │   └── Icon.svelte
│       │   │   ├── NoData.svelte
│       │   │   └── Markdown.svelte
│       │   └── Tooltip.svelte
│       └── SearchInput.svelte
│           └── Icon.svelte
├── AttestationPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── OrgUnitFieldPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
├── TagPicker.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
├── AliasPicker.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
├── ComplexityKindPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── MeasurableCategoryPicker.svelte
│   ├── Grid.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   └── MeasurableTreeSelector.svelte
│       ├── MeasurableTreeNode.svelte
│       │   └── Icon.svelte
│       └── SearchInput.svelte
│           └── Icon.svelte
├── EntityStatisticPicker.svelte
│   └── EntityStatisticTreeSelector.svelte
│       ├── SearchInput.svelte
│       │   └── Icon.svelte
│       └── StatisticTreeNode.svelte
│           └── Icon.svelte
└── MeasurablePicker.svelte
    ├── Icon.svelte
    ├── MeasurableTreeSelector.svelte
    │   ├── MeasurableTreeNode.svelte
    │   │   └── Icon.svelte
    │   └── SearchInput.svelte
    │       └── Icon.svelte
    ├── Grid.svelte
    │   └── Icon.svelte
    └── NoData.svelte
```

**survey/components/svelte/inline-panel/EntityResponseRenderer.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**common/svelte/EntitySearchSelector.svelte**

```
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/EntitySelector.svelte**

```
├── Icon.svelte
└── EntityPicker.svelte
    ├── InvolvementPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    ├── CostKindPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    ├── SurveyQuestionPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   ├── Icon.svelte
    │   └── Toggle.svelte
    │       └── Icon.svelte
    ├── AssessmentDefinitionPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    ├── PersonPicker.svelte
    │   ├── Icon.svelte
    │   └── EntitySearchSelector.svelte
    │       └── EntityLabel.svelte
    │           └── EntityIcon.svelte
    │               └── Icon.svelte
    ├── SurveyInstanceFieldPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   ├── Icon.svelte
    │   ├── NoData.svelte
    │   └── Toggle.svelte
    │       └── Icon.svelte
    ├── ApplicationFieldPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   ├── Icon.svelte
    │   └── NoData.svelte
    ├── ChangeInitiativeFieldPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   ├── Icon.svelte
    │   └── NoData.svelte
    ├── AppGroupPicker.svelte
    │   ├── Icon.svelte
    │   └── EntitySearchSelector.svelte
    │       └── EntityLabel.svelte
    │           └── EntityIcon.svelte
    │               └── Icon.svelte
    ├── DataTypePicker.svelte
    │   └── DataTypeTreeSelector.svelte
    │       ├── DataTypeTreeNode.svelte
    │       │   ├── Icon.svelte
    │       │   ├── RatingCharacteristicsDecorator.svelte
    │       │   │   └── FlowRatingCell.svelte
    │       │   ├── UsageCharacteristicsDecorator.svelte
    │       │   │   └── Icon.svelte
    │       │   ├── DataTypeNodeTooltipContent.svelte
    │       │   │   ├── Icon.svelte
    │       │   │   ├── RatingIndicatorCell.svelte
    │       │   │   │   └── Icon.svelte
    │       │   │   ├── DescriptionFade.svelte
    │       │   │   │   ├── Markdown.svelte
    │       │   │   │   └── Icon.svelte
    │       │   │   ├── NoData.svelte
    │       │   │   └── Markdown.svelte
    │       │   └── Tooltip.svelte
    │       └── SearchInput.svelte
    │           └── Icon.svelte
    ├── AttestationPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    ├── OrgUnitFieldPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   ├── Icon.svelte
    │   └── NoData.svelte
    ├── TagPicker.svelte
    │   ├── Icon.svelte
    │   └── NoData.svelte
    ├── AliasPicker.svelte
    │   ├── Icon.svelte
    │   └── NoData.svelte
    ├── ComplexityKindPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    ├── MeasurableCategoryPicker.svelte
    │   ├── Grid.svelte
    │   │   └── Icon.svelte
    │   ├── Icon.svelte
    │   └── MeasurableTreeSelector.svelte
    │       ├── MeasurableTreeNode.svelte
    │       │   └── Icon.svelte
    │       └── SearchInput.svelte
    │           └── Icon.svelte
    ├── EntityStatisticPicker.svelte
    │   └── EntityStatisticTreeSelector.svelte
    │       ├── SearchInput.svelte
    │       │   └── Icon.svelte
    │       └── StatisticTreeNode.svelte
    │           └── Icon.svelte
    └── MeasurablePicker.svelte
        ├── Icon.svelte
        ├── MeasurableTreeSelector.svelte
        │   ├── MeasurableTreeNode.svelte
        │   │   └── Icon.svelte
        │   └── SearchInput.svelte
        │       └── Icon.svelte
        ├── Grid.svelte
        │   └── Icon.svelte
        └── NoData.svelte
```

**common/svelte/entity-pickers/EntityStatisticPicker.svelte**

```
└── EntityStatisticTreeSelector.svelte
    ├── SearchInput.svelte
    │   └── Icon.svelte
    └── StatisticTreeNode.svelte
        └── Icon.svelte
```

**common/svelte/EntityStatisticTreeSelector.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
└── StatisticTreeNode.svelte
    └── Icon.svelte
```

**physical-flows/svelte/EnumHelpPanel.svelte**

_No dependencies_

**physical-flows/svelte/EnumSelect.svelte**

```
├── Icon.svelte
└── EnumHelpPanel.svelte
```

**technology/svelte/custom-environment-panel/EnvironmentRegistration.svelte**

```
└── Icon.svelte
```

**technology/svelte/custom-environment-panel/EnvironmentRemovalConfirmation.svelte**

_No dependencies_

**system/svelte/euda-list/EudaListPanel.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── NoData.svelte
├── Icon.svelte
├── EndUserApplicationInfoPanel.svelte
│   └── DescriptionFade.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── SubSection.svelte
```

**process-diagram/components/process-diagram/svg-elems/Event.svelte**

_No dependencies_

**process-diagram/components/process-diagram/context-panels/EventContextPanel.svelte**

```
└── ConnectionsSubContextPanel.svelte
```

**process-diagram/components/process-diagram/svg-elems/sub-types/Exclusive.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/filter-selector/FilterList.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**report-grid/components/svelte/FilterNotePopoverContent.svelte**

```
├── Markdown.svelte
└── Icon.svelte
```

**aggregate-overlay-diagram/components/filter-selector/FilterPanel.svelte**

```
├── FilterSelectorPanel.svelte
│   ├── AssessmentRatingPicker.svelte
│   │   ├── AssessmentDefinitionPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── RatingPicker.svelte
│   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   └── Markdown.svelte
│   └── Icon.svelte
├── FilterList.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**aggregate-overlay-diagram/components/filter-selector/FilterSelectorPanel.svelte**

```
├── AssessmentRatingPicker.svelte
│   ├── AssessmentDefinitionPicker.svelte
│   │   ├── Grid.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── RatingPicker.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   └── Markdown.svelte
└── Icon.svelte
```

**report-grid/components/svelte/column-definition-edit-panel/FixedColumnDetailsEditor.svelte**

```
├── DropdownPicker.svelte
│   └── Icon.svelte
├── Icon.svelte
└── ColumnDefinitionHeader.svelte
    └── DescriptionFade.svelte
        ├── Markdown.svelte
        └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/Flow.svelte**

_No dependencies_

**data-flow/components/svelte/flow-detail-tab/filters/FlowClassificationFilters.svelte**

```
└── RatingIndicatorCell.svelte
    └── Icon.svelte
```

**data-types/components/data-type-decorator-section/filters/FlowClassificationFilters.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**flow-classification-rule/components/svelte/FlowClassificationLegend.svelte**

_No dependencies_

**flow-classification-rule/components/summary-list/FlowClassificationRuleDetail.svelte**

```
├── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**flow-classification-rule/components/summary-list/FlowClassificationRuleEditor.svelte**

```
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── Icon.svelte
└── DataTypeTreeSelector.svelte
    ├── DataTypeTreeNode.svelte
    │   ├── Icon.svelte
    │   ├── RatingCharacteristicsDecorator.svelte
    │   │   └── FlowRatingCell.svelte
    │   ├── UsageCharacteristicsDecorator.svelte
    │   │   └── Icon.svelte
    │   ├── DataTypeNodeTooltipContent.svelte
    │   │   ├── Icon.svelte
    │   │   ├── RatingIndicatorCell.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   └── Markdown.svelte
    │   └── Tooltip.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

**flow-classification-rule/components/svelte/FlowClassificationRuleOverview.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── Icon.svelte
├── AssessmentFavouritesList.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── AssessmentRatingTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   ├── Icon.svelte
│   │   └── LastEdited.svelte
│   │       └── DateTime.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
├── SubSection.svelte
└── Markdown.svelte
```

**flow-classification-rule/components/summary-list/FlowClassificationRulesPanel.svelte**

```
├── FlowClassificationRulesTable.svelte
│   ├── LoadingPlaceholder.svelte
│   │   └── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── ViewLink.svelte
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
├── Icon.svelte
└── FlowClassificationRuleEditor.svelte
    ├── EntitySearchSelector.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    ├── Icon.svelte
    └── DataTypeTreeSelector.svelte
        ├── DataTypeTreeNode.svelte
        │   ├── Icon.svelte
        │   ├── RatingCharacteristicsDecorator.svelte
        │   │   └── FlowRatingCell.svelte
        │   ├── UsageCharacteristicsDecorator.svelte
        │   │   └── Icon.svelte
        │   ├── DataTypeNodeTooltipContent.svelte
        │   │   ├── Icon.svelte
        │   │   ├── RatingIndicatorCell.svelte
        │   │   │   └── Icon.svelte
        │   │   ├── DescriptionFade.svelte
        │   │   │   ├── Markdown.svelte
        │   │   │   └── Icon.svelte
        │   │   ├── NoData.svelte
        │   │   └── Markdown.svelte
        │   └── Tooltip.svelte
        └── SearchInput.svelte
            └── Icon.svelte
```

**flow-classification-rule/components/table/FlowClassificationRulesTable.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**data-types/components/data-type-decorator-section/context-panel/FlowClassificationRuleViewTable.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── RatingIndicatorCell.svelte
    └── Icon.svelte
```

**data-flow/components/svelte/FlowContextPanel.svelte**

```
├── FlowDecoratorGraphFilters.svelte
│   ├── AssessmentFilters.svelte
│   │   ├── Icon.svelte
│   │   └── NoData.svelte
│   ├── DefaultFilters.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── DefaultContextPanel.svelte
│   ├── Icon.svelte
│   └── Toggle.svelte
│       └── Icon.svelte
├── ClientContextPanel.svelte
│   ├── Icon.svelte
│   ├── EntityInfoPanel.svelte
│   │   ├── ApplicationInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── ChangeInitiativeInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── MeasurableInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── ActorInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── DataTypeInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── LogicalDataFlowInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   └── KeyAssessmentInfoPanel.svelte
│   │   ├── SurveyInstanceInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── DateTime.svelte
│   │   │   ├── DescriptionFade.svelte
│   │   │   │   ├── Markdown.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── DatePicker.svelte
│   │   │       └── Icon.svelte
│   │   ├── PersonInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   └── EndUserApplicationInfoPanel.svelte
│   │       └── DescriptionFade.svelte
│   │           ├── Markdown.svelte
│   │           └── Icon.svelte
│   └── NoData.svelte
└── FlowDetailContextPanel.svelte
    ├── Icon.svelte
    ├── PhysicalFlowDetailTable.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── NoData.svelte
    │   └── Icon.svelte
    ├── DataTypeDetailTable.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   ├── Icon.svelte
    │   └── NoData.svelte
    └── EntityLink.svelte
        ├── ViewLink.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**physical-flows/svelte/FlowCreator.svelte**

```
├── Icon.svelte
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**logical-flow/components/edit/svelte/FlowDataTypeEditor.svelte**

```
├── SuggestedDataTypeTreeSelector.svelte
│   └── DataTypeTreeSelector.svelte
│       ├── DataTypeTreeNode.svelte
│       │   ├── Icon.svelte
│       │   ├── RatingCharacteristicsDecorator.svelte
│       │   │   └── FlowRatingCell.svelte
│       │   ├── UsageCharacteristicsDecorator.svelte
│       │   │   └── Icon.svelte
│       │   ├── DataTypeNodeTooltipContent.svelte
│       │   │   ├── Icon.svelte
│       │   │   ├── RatingIndicatorCell.svelte
│       │   │   │   └── Icon.svelte
│       │   │   ├── DescriptionFade.svelte
│       │   │   │   ├── Markdown.svelte
│       │   │   │   └── Icon.svelte
│       │   │   ├── NoData.svelte
│       │   │   └── Markdown.svelte
│       │   └── Tooltip.svelte
│       └── SearchInput.svelte
│           └── Icon.svelte
├── Icon.svelte
└── SavingPlaceholder.svelte
    └── Icon.svelte
```

**data-flow/components/svelte/FlowDecoratorExplorerPanel.svelte**

```
├── Categories.svelte
├── Clients.svelte
├── Arcs.svelte
├── FlowContextPanel.svelte
│   ├── FlowDecoratorGraphFilters.svelte
│   │   ├── AssessmentFilters.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── DefaultFilters.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── DefaultContextPanel.svelte
│   │   ├── Icon.svelte
│   │   └── Toggle.svelte
│   │       └── Icon.svelte
│   ├── ClientContextPanel.svelte
│   │   ├── Icon.svelte
│   │   ├── EntityInfoPanel.svelte
│   │   │   ├── ApplicationInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── ChangeInitiativeInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── MeasurableInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── ActorInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── DataTypeInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── LogicalDataFlowInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   └── KeyAssessmentInfoPanel.svelte
│   │   │   ├── SurveyInstanceInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── DateTime.svelte
│   │   │   │   ├── DescriptionFade.svelte
│   │   │   │   │   ├── Markdown.svelte
│   │   │   │   │   └── Icon.svelte
│   │   │   │   └── DatePicker.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── PersonInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── EndUserApplicationInfoPanel.svelte
│   │   │       └── DescriptionFade.svelte
│   │   │           ├── Markdown.svelte
│   │   │           └── Icon.svelte
│   │   └── NoData.svelte
│   └── FlowDetailContextPanel.svelte
│       ├── Icon.svelte
│       ├── PhysicalFlowDetailTable.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── NoData.svelte
│       │   └── Icon.svelte
│       ├── DataTypeDetailTable.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── Icon.svelte
│       │   └── NoData.svelte
│       └── EntityLink.svelte
│           ├── ViewLink.svelte
│           └── EntityLabel.svelte
│               └── EntityIcon.svelte
│                   └── Icon.svelte
├── Icon.svelte
└── NoData.svelte
```

**data-types/components/data-type-decorator-section/filters/FlowDecoratorFilters.svelte**

```
├── Icon.svelte
├── AssessmentFilters.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── FlowClassificationFilters.svelte
    ├── RatingIndicatorCell.svelte
    │   └── Icon.svelte
    └── NoData.svelte
```

**data-flow/components/svelte/FlowDecoratorGraphFilters.svelte**

```
├── AssessmentFilters.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
├── DefaultFilters.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**data-flow/components/svelte/FlowDetailContextPanel.svelte**

```
├── Icon.svelte
├── PhysicalFlowDetailTable.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── NoData.svelte
│   └── Icon.svelte
├── DataTypeDetailTable.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── Icon.svelte
│   └── NoData.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/filters/FlowDetailFilters.svelte**

```
├── Icon.svelte
├── AssessmentFilters.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
├── InboundOutboundFilters.svelte
├── PhysicalFlowAttributeFilters.svelte
│   └── NoData.svelte
├── FlowClassificationFilters.svelte
│   └── RatingIndicatorCell.svelte
│       └── Icon.svelte
└── SourceTargetKindFilters.svelte
    └── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/FlowDetailPanel.svelte**

```
├── LogicalFlowTable.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── PhysicalFlowTable.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── SelectedFlowDetailPanel.svelte
│   ├── Icon.svelte
│   ├── SelectedLogicalFlowDetail.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── Icon.svelte
│   │   ├── DataTypeMiniTable.svelte
│   │   │   ├── EntityLabel.svelte
│   │   │   │   └── EntityIcon.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── Toggle.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── FlowRatingCell.svelte
│   │   │   └── RatingIndicatorCell.svelte
│   │   │       └── Icon.svelte
│   │   ├── AssessmentsTable.svelte
│   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── EntityIcon.svelte
│   │   │       └── Icon.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── SelectedPhysicalFlowDetail.svelte
│       ├── EntityLink.svelte
│       │   ├── ViewLink.svelte
│       │   └── EntityLabel.svelte
│       │       └── EntityIcon.svelte
│       │           └── Icon.svelte
│       ├── Icon.svelte
│       ├── DescriptionFade.svelte
│       │   ├── Markdown.svelte
│       │   └── Icon.svelte
│       ├── DataTypeMiniTable.svelte
│       │   ├── EntityLabel.svelte
│       │   │   └── EntityIcon.svelte
│       │   │       └── Icon.svelte
│       │   ├── Toggle.svelte
│       │   │   └── Icon.svelte
│       │   ├── FlowRatingCell.svelte
│       │   └── RatingIndicatorCell.svelte
│       │       └── Icon.svelte
│       ├── AssessmentsTable.svelte
│       │   ├── RatingIndicatorCell.svelte
│       │   │   └── Icon.svelte
│       │   └── EntityIcon.svelte
│       │       └── Icon.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── DataExtractLink.svelte
│   └── Icon.svelte
└── FlowDetailFilters.svelte
    ├── Icon.svelte
    ├── AssessmentFilters.svelte
    │   ├── RatingIndicatorCell.svelte
    │   │   └── Icon.svelte
    │   ├── NoData.svelte
    │   └── EntityIcon.svelte
    │       └── Icon.svelte
    ├── InboundOutboundFilters.svelte
    ├── PhysicalFlowAttributeFilters.svelte
    │   └── NoData.svelte
    ├── FlowClassificationFilters.svelte
    │   └── RatingIndicatorCell.svelte
    │       └── Icon.svelte
    └── SourceTargetKindFilters.svelte
        └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/FlowDiagram.svelte**

```
├── NodeLayer.svelte
│   └── Node.svelte
├── FlowLayer.svelte
│   └── Flow.svelte
├── AnnotationLayer.svelte
│   └── Annotation.svelte
├── ContextPanel.svelte
│   ├── NodePanel.svelte
│   │   ├── AddLogicalFlowSubPanel.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── AddAnnotationSubPanel.svelte
│   │   └── Icon.svelte
│   ├── DefaultPanel.svelte
│   │   ├── AddNodeSubPanel.svelte
│   │   │   ├── EntitySearchSelector.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── FlowPanel.svelte
│   │   ├── AddAnnotationSubPanel.svelte
│   │   ├── AddPhysicalFlowSubPanel.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── Icon.svelte
│   ├── LastEdited.svelte
│   │   └── DateTime.svelte
│   ├── AnnotationPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── AddAnnotationSubPanel.svelte
│   ├── Icon.svelte
│   ├── OverlayGroupsPanel.svelte
│   │   ├── Icon.svelte
│   │   ├── AddOverlayGroupEntrySubPanel.svelte
│   │   │   ├── GroupSelectorPanel.svelte
│   │   │   │   ├── EntitySearchSelector.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   └── MeasurableAlignmentTreeSelector.svelte
│   │   │   │       ├── SearchInput.svelte
│   │   │   │       │   └── Icon.svelte
│   │   │   │       └── MeasurableAlignmentTreeNode.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── EntitySearchSelector.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   └── EditOverlayIconSubPanel.svelte
│   │   │       ├── Icon.svelte
│   │   │       ├── ColorPicker.svelte
│   │   │       ├── OverlayGlyph.svelte
│   │   │       ├── SymbolPicker.svelte
│   │   │       └── EntityLink.svelte
│   │   │           ├── ViewLink.svelte
│   │   │           └── EntityLabel.svelte
│   │   │               └── EntityIcon.svelte
│   │   │                   └── Icon.svelte
│   │   ├── OverlayGlyph.svelte
│   │   ├── EditOverlayIconSubPanel.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── ColorPicker.svelte
│   │   │   ├── OverlayGlyph.svelte
│   │   │   ├── SymbolPicker.svelte
│   │   │   └── EntityLink.svelte
│   │   │       ├── ViewLink.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── CreateNewOverlayGroupPanel.svelte
│   │   │   └── Icon.svelte
│   │   ├── EntityLabel.svelte
│   │   │   └── EntityIcon.svelte
│   │   │       └── Icon.svelte
│   │   └── ImportOverlayGroupSubPanel.svelte
│   │       └── EntitySearchSelector.svelte
│   │           └── EntityLabel.svelte
│   │               └── EntityIcon.svelte
│   │                   └── Icon.svelte
│   ├── EditFlowDiagramOverviewPanel.svelte
│   │   └── Icon.svelte
│   ├── VisibilityToggles.svelte
│   ├── RelatedEntitiesPanel.svelte
│   │   ├── RelatedEntitiesViewTable.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── RelatedEntityAddNodesPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── RelatedEntityAddFlowsPanel.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── AddRelatedMeasurableSubPanel.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── AddRelatedChangeInitiativeSubPanel.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   └── AddRelatedDataTypeSubPanel.svelte
│   │       └── EntitySearchSelector.svelte
│   │           └── EntityLabel.svelte
│   │               └── EntityIcon.svelte
│   │                   └── Icon.svelte
│   ├── CloneDiagramSubPanel.svelte
│   │   ├── Icon.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── RemoveDiagramSubPanel.svelte
│   │   └── Icon.svelte
│   └── Markdown.svelte
├── NoData.svelte
└── ImageDownloadLink.svelte
    └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/FlowLayer.svelte**

```
└── Flow.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/FlowPanel.svelte**

```
├── AddAnnotationSubPanel.svelte
├── AddPhysicalFlowSubPanel.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**common/svelte/FlowRatingCell.svelte**

_No dependencies_

**logical-flow/svelte/flow-venn/FlowVenn.svelte**

```
└── SingleVenn.svelte
```

**common/svelte/Grid.svelte**

```
└── Icon.svelte
```

**common/svelte/GridWithCellRenderer.svelte**

```
└── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/Group.svelte**

```
└── Unit.svelte
    └── GroupLeader.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder-controls/GroupControls.svelte**

```
├── EditGroupPanel.svelte
│   └── ColorPicker.svelte
├── EditItemsPanel.svelte
│   ├── EntityPicker.svelte
│   │   ├── InvolvementPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── CostKindPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── SurveyQuestionPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── Toggle.svelte
│   │   │       └── Icon.svelte
│   │   ├── AssessmentDefinitionPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── PersonPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── SurveyInstanceFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── NoData.svelte
│   │   │   └── Toggle.svelte
│   │   │       └── Icon.svelte
│   │   ├── ApplicationFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── ChangeInitiativeFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── AppGroupPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── DataTypePicker.svelte
│   │   │   └── DataTypeTreeSelector.svelte
│   │   │       ├── DataTypeTreeNode.svelte
│   │   │       │   ├── Icon.svelte
│   │   │       │   ├── RatingCharacteristicsDecorator.svelte
│   │   │       │   │   └── FlowRatingCell.svelte
│   │   │       │   ├── UsageCharacteristicsDecorator.svelte
│   │   │       │   │   └── Icon.svelte
│   │   │       │   ├── DataTypeNodeTooltipContent.svelte
│   │   │       │   │   ├── Icon.svelte
│   │   │       │   │   ├── RatingIndicatorCell.svelte
│   │   │       │   │   │   └── Icon.svelte
│   │   │       │   │   ├── DescriptionFade.svelte
│   │   │       │   │   │   ├── Markdown.svelte
│   │   │       │   │   │   └── Icon.svelte
│   │   │       │   │   ├── NoData.svelte
│   │   │       │   │   └── Markdown.svelte
│   │   │       │   └── Tooltip.svelte
│   │   │       └── SearchInput.svelte
│   │   │           └── Icon.svelte
│   │   ├── AttestationPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── OrgUnitFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── TagPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── AliasPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── ComplexityKindPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── MeasurableCategoryPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── MeasurableTreeSelector.svelte
│   │   │       ├── MeasurableTreeNode.svelte
│   │   │       │   └── Icon.svelte
│   │   │       └── SearchInput.svelte
│   │   │           └── Icon.svelte
│   │   ├── EntityStatisticPicker.svelte
│   │   │   └── EntityStatisticTreeSelector.svelte
│   │   │       ├── SearchInput.svelte
│   │   │       │   └── Icon.svelte
│   │   │       └── StatisticTreeNode.svelte
│   │   │           └── Icon.svelte
│   │   └── MeasurablePicker.svelte
│   │       ├── Icon.svelte
│   │       ├── MeasurableTreeSelector.svelte
│   │       │   ├── MeasurableTreeNode.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── SearchInput.svelte
│   │       │       └── Icon.svelte
│   │       ├── Grid.svelte
│   │       │   └── Icon.svelte
│   │       └── NoData.svelte
│   ├── DropdownPicker.svelte
│   │   └── Icon.svelte
│   ├── ColorPicker.svelte
│   └── Icon.svelte
├── GroupDetailsPanel.svelte
│   └── Icon.svelte
├── AddItemsPanel.svelte
│   ├── EntityPicker.svelte
│   │   ├── InvolvementPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── CostKindPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── SurveyQuestionPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── Toggle.svelte
│   │   │       └── Icon.svelte
│   │   ├── AssessmentDefinitionPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── PersonPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── SurveyInstanceFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── NoData.svelte
│   │   │   └── Toggle.svelte
│   │   │       └── Icon.svelte
│   │   ├── ApplicationFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── ChangeInitiativeFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── AppGroupPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   ├── DataTypePicker.svelte
│   │   │   └── DataTypeTreeSelector.svelte
│   │   │       ├── DataTypeTreeNode.svelte
│   │   │       │   ├── Icon.svelte
│   │   │       │   ├── RatingCharacteristicsDecorator.svelte
│   │   │       │   │   └── FlowRatingCell.svelte
│   │   │       │   ├── UsageCharacteristicsDecorator.svelte
│   │   │       │   │   └── Icon.svelte
│   │   │       │   ├── DataTypeNodeTooltipContent.svelte
│   │   │       │   │   ├── Icon.svelte
│   │   │       │   │   ├── RatingIndicatorCell.svelte
│   │   │       │   │   │   └── Icon.svelte
│   │   │       │   │   ├── DescriptionFade.svelte
│   │   │       │   │   │   ├── Markdown.svelte
│   │   │       │   │   │   └── Icon.svelte
│   │   │       │   │   ├── NoData.svelte
│   │   │       │   │   └── Markdown.svelte
│   │   │       │   └── Tooltip.svelte
│   │   │       └── SearchInput.svelte
│   │   │           └── Icon.svelte
│   │   ├── AttestationPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── OrgUnitFieldPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── TagPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── AliasPicker.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── NoData.svelte
│   │   ├── ComplexityKindPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   ├── MeasurableCategoryPicker.svelte
│   │   │   ├── Grid.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── MeasurableTreeSelector.svelte
│   │   │       ├── MeasurableTreeNode.svelte
│   │   │       │   └── Icon.svelte
│   │   │       └── SearchInput.svelte
│   │   │           └── Icon.svelte
│   │   ├── EntityStatisticPicker.svelte
│   │   │   └── EntityStatisticTreeSelector.svelte
│   │   │       ├── SearchInput.svelte
│   │   │       │   └── Icon.svelte
│   │   │       └── StatisticTreeNode.svelte
│   │   │           └── Icon.svelte
│   │   └── MeasurablePicker.svelte
│   │       ├── Icon.svelte
│   │       ├── MeasurableTreeSelector.svelte
│   │       │   ├── MeasurableTreeNode.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── SearchInput.svelte
│   │       │       └── Icon.svelte
│   │       ├── Grid.svelte
│   │       │   └── Icon.svelte
│   │       └── NoData.svelte
│   ├── DropdownPicker.svelte
│   │   └── Icon.svelte
│   ├── ColorPicker.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder-controls/GroupDetailsPanel.svelte**

```
└── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/GroupLeader.svelte**

```
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**playpen/3/builder/GroupRow.svelte**

```
└── EntityCell.svelte
    ├── StatisticsBox.svelte
    └── CalloutBox.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/GroupSelectorPanel.svelte**

```
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── MeasurableAlignmentTreeSelector.svelte
    ├── SearchInput.svelte
    │   └── Icon.svelte
    └── MeasurableAlignmentTreeNode.svelte
        └── Icon.svelte
```

**common/svelte/Icon.svelte**

_No dependencies_

**common/svelte/ImageDownloadLink.svelte**

```
└── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/ImportOverlayGroupSubPanel.svelte**

```
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/filters/InboundOutboundFilters.svelte**

_No dependencies_

**process-diagram/components/process-diagram/svg-elems/sub-types/Inclusive.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/instance-selector/InstanceCreatePanel.svelte**

```
└── Icon.svelte
```

**involvement-kind/components/svelte/InvolvementBreakdown.svelte**

```
└── Icon.svelte
```

**involvement-kind/components/svelte/InvolvementKindOverview.svelte**

```
├── Icon.svelte
├── EditInvolvementKindPanel.svelte
│   ├── Icon.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── EntityIcon.svelte
    └── Icon.svelte
```

**involvement-kind/components/svelte/InvolvementKindPanel.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── InvolvementKindOverview.svelte
│   ├── Icon.svelte
│   ├── EditInvolvementKindPanel.svelte
│   │   ├── Icon.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
├── AssessmentFavouritesList.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── AssessmentRatingTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   ├── Icon.svelte
│   │   └── LastEdited.svelte
│   │       └── DateTime.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
└── SubSection.svelte
```

**involvement-kind/components/svelte/InvolvementKindTable.svelte**

```
├── InvolvementBreakdown.svelte
│   └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── Icon.svelte
└── DropdownPicker.svelte
    └── Icon.svelte
```

**involvement-kind/components/svelte/InvolvementListPanel.svelte**

```
├── InvolvementBreakdown.svelte
│   └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**common/svelte/entity-pickers/InvolvementPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**involvement-kind/components/svelte/InvolvementsSection.svelte**

```
├── InvolvementListPanel.svelte
│   ├── InvolvementBreakdown.svelte
│   │   └── Icon.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── SearchInput.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
└── BulkInvolvementLoader.svelte
    ├── Icon.svelte
    ├── Tooltip.svelte
    └── LoaderErrorTooltipContent.svelte
```

**system/svelte/ratings-schemes/ItemEditor.svelte**

```
├── ColorPicker.svelte
└── Icon.svelte
```

**system/svelte/ratings-schemes/ItemPreviewBar.svelte**

_No dependencies_

**system/svelte/ratings-schemes/ItemRemovalConfirmation.svelte**

```
└── Icon.svelte
```

**system/svelte/ratings-schemes/ItemsView.svelte**

```
├── Icon.svelte
├── ItemEditor.svelte
│   ├── ColorPicker.svelte
│   └── Icon.svelte
└── ItemRemovalConfirmation.svelte
    └── Icon.svelte
```

**common/svelte/info-panels/KeyAssessmentInfoPanel.svelte**

_No dependencies_

**common/svelte/info-panels/KeyInvolvementInfoPanel.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**common/svelte/LastEdited.svelte**

```
└── DateTime.svelte
```

**legal-entity/pages/view/LegalEntityOverview.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── NoData.svelte
├── AssessmentFavouritesList.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── AssessmentRatingTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   ├── Icon.svelte
│   │   └── LastEdited.svelte
│   │       └── DateTime.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
└── SubSection.svelte
```

**legal-entity-relationship-kind/pages/list/LegalEntityRelationshipKindListView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── NoData.svelte
└── Icon.svelte
```

**legal-entity-relationship-kind/pages/view/LegalEntityRelationshipKindOverview.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
└── NoData.svelte
```

**legal-entity-relationship/pages/view/LegalEntityRelationshipOverview.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── NoData.svelte
├── AssessmentFavouritesList.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── AssessmentRatingTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   ├── Icon.svelte
│   │   └── LastEdited.svelte
│   │       └── DateTime.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
├── SubSection.svelte
├── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**survey/components/svelte/inline-panel/LegalEntityResponseRenderer.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**system/svelte/licences/LicencesAdminView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── Section.svelte
│   └── Icon.svelte
├── EditLicencePanel.svelte
├── Icon.svelte
├── RemoveLicencePanel.svelte
└── NoData.svelte
```

**system/svelte/analytics-dashboard/LineChart.svelte**

```
├── LoadingPlaceholder.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**involvement-kind/components/svelte/LoaderErrorTooltipContent.svelte**

_No dependencies_

**common/svelte/LoadingPlaceholder.svelte**

```
└── Icon.svelte
```

**common/svelte/info-panels/LogicalDataFlowInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── KeyInvolvementInfoPanel.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
└── KeyAssessmentInfoPanel.svelte
```

**physical-flows/svelte/LogicalFlowLabel.svelte**

```
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── Icon.svelte
```

**physical-flows/svelte/LogicalFlowSelectionStep.svelte**

```
├── RouteSelector.svelte
│   ├── EntityLabel.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   ├── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── LogicalFlowLabel.svelte
│   ├── EntityLabel.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   └── Icon.svelte
├── Icon.svelte
├── StepHeader.svelte
│   ├── Check.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
└── FlowCreator.svelte
    ├── Icon.svelte
    ├── EntitySearchSelector.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/LogicalFlowTable.svelte**

```
└── SearchInput.svelte
    └── Icon.svelte
```

**common/svelte/Markdown.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/MeasurableAlignmentTreeNode.svelte**

```
└── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/MeasurableAlignmentTreeSelector.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
└── MeasurableAlignmentTreeNode.svelte
    └── Icon.svelte
```

**attestation/components/svelte/MeasurableAttestationPanel.svelte**

```
├── NoData.svelte
├── MeasurableAttestationSubPanel.svelte
│   ├── Icon.svelte
│   ├── SubSection.svelte
│   ├── NoData.svelte
│   ├── MiniActions.svelte
│   │   └── Icon.svelte
│   ├── DateTime.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
└── LoadingPlaceholder.svelte
    └── Icon.svelte
```

**attestation/components/svelte/MeasurableAttestationSubPanel.svelte**

```
├── Icon.svelte
├── SubSection.svelte
├── NoData.svelte
├── MiniActions.svelte
│   └── Icon.svelte
├── DateTime.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**system/svelte/measurable-categories/MeasurableCategoryEditView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
└── Icon.svelte
```

**system/svelte/measurable-categories/MeasurableCategoryListView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── SearchInput.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**system/svelte/nav-aid-builder/MeasurableCategoryNavAidBuilder.svelte**

```
└── TaxonomyNavAidBuilder.svelte
    └── ColorPicker.svelte
```

**report-grid/components/svelte/pickers/MeasurableCategoryPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
└── MeasurableTreeSelector.svelte
    ├── MeasurableTreeNode.svelte
    │   └── Icon.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

**system/svelte/assessment-definitions/MeasurableCategoryPicker.svelte**

_No dependencies_

**measurable-rating/svelte/MeasurableHierarchyNode.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**measurable-rating/svelte/MeasurableHierarchyTooltip.svelte**

```
└── MeasurableHierarchyNode.svelte
    └── EntityLink.svelte
        ├── ViewLink.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**common/svelte/info-panels/MeasurableInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── KeyInvolvementInfoPanel.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── KeyAssessmentInfoPanel.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**survey/components/svelte/inline-panel/MeasurableMultiResponseRenderer.svelte**

```
└── MeasurableTree.svelte
    └── EntityLink.svelte
        ├── ViewLink.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**common/svelte/entity-pickers/MeasurablePicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
└── MeasurableTreeSelector.svelte
    ├── MeasurableTreeNode.svelte
    │   └── Icon.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

**report-grid/components/svelte/pickers/MeasurablePicker.svelte**

```
├── Icon.svelte
├── MeasurableTreeSelector.svelte
│   ├── MeasurableTreeNode.svelte
│   │   └── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── Grid.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**measurable-rating/svelte/MeasurableRatingOverview.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── SubSection.svelte
├── AssessmentFavouritesList.svelte
│   ├── Icon.svelte
│   ├── Tooltip.svelte
│   ├── AssessmentRatingTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   ├── Icon.svelte
│   │   └── LastEdited.svelte
│   │       └── DateTime.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   ├── AssessmentDefinitionTooltipContent.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── NoData.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── Icon.svelte
└── DescriptionFade.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**measurable-rating/components/view-grid/MeasurableRatingsViewGrid.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── DataExtractLink.svelte
│   └── Icon.svelte
├── NoData.svelte
└── LoadingPlaceholder.svelte
    └── Icon.svelte
```

**measurable-rating/svelte/MeasurableRatingTable.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── NoData.svelte
├── Icon.svelte
├── Tooltip.svelte
├── ReplacementAppMiniTable.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── AssessmentRatingsMiniTable.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── MeasurableRatingViewIconTooltip.svelte
│   └── Icon.svelte
└── MeasurableHierarchyTooltip.svelte
    └── MeasurableHierarchyNode.svelte
        └── EntityLink.svelte
            ├── ViewLink.svelte
            └── EntityLabel.svelte
                └── EntityIcon.svelte
                    └── Icon.svelte
```

**measurable-rating/svelte/MeasurableRatingViewIconTooltip.svelte**

```
└── Icon.svelte
```

**survey/components/svelte/inline-panel/MeasurableTree.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**common/svelte/MeasurableTreeNode.svelte**

```
└── Icon.svelte
```

**common/svelte/MeasurableTreeSelector.svelte**

```
├── MeasurableTreeNode.svelte
│   └── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**process-diagram/components/process-diagram/svg-elems/sub-types/Message.svelte**

_No dependencies_

**common/svelte/MiniActions.svelte**

```
└── Icon.svelte
```

**common/svelte/calendar-heatmap/Month.svelte**

```
├── Day.svelte
└── Weeks.svelte
```

**system/svelte/nav-aid-builder/NavAidBuilder.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── TaxonomyNavAidBuilder.svelte
│   └── ColorPicker.svelte
├── DataTypeNavAidBuilder.svelte
│   └── TaxonomyNavAidBuilder.svelte
│       └── ColorPicker.svelte
├── MeasurableCategoryNavAidBuilder.svelte
│   └── TaxonomyNavAidBuilder.svelte
│       └── ColorPicker.svelte
└── PersonNavAidBuilder.svelte
    ├── BuilderControl.svelte
    │   ├── NavTree.svelte
    │   │   └── Icon.svelte
    │   ├── RemovalConfirmation.svelte
    │   ├── BlockEditor.svelte
    │   └── PersonEditor.svelte
    │       ├── EntitySearchSelector.svelte
    │       │   └── EntityLabel.svelte
    │       │       └── EntityIcon.svelte
    │       │           └── Icon.svelte
    │       └── Icon.svelte
    ├── PersonNavAid.svelte
    │   ├── GroupLeader.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   └── Group.svelte
    │       └── Unit.svelte
    │           └── GroupLeader.svelte
    │               └── EntityLabel.svelte
    │                   └── EntityIcon.svelte
    │                       └── Icon.svelte
    └── Toggle.svelte
        └── Icon.svelte
```

**system/svelte/nav-aids/NavAidEditView.svelte**

_No dependencies_

**system/svelte/nav-aids/NavAidRemovalConfirmation.svelte**

_No dependencies_

**system/svelte/nav-aids/NavAidsAdminView.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── Icon.svelte
├── NavAidEditView.svelte
├── NavAidRemovalConfirmation.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**process-diagram/components/process-diagram/svg-elems/NavigationCell.svelte**

_No dependencies_

**process-diagram/components/process-diagram/context-panels/NavigationCellContextPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Markdown.svelte
```

**system/svelte/nav-aid-builder/custom/control/NavTree.svelte**

```
└── Icon.svelte
```

**common/svelte/NoData.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/Node.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/NodeLayer.svelte**

```
└── Node.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/NodePanel.svelte**

```
├── AddLogicalFlowSubPanel.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── AddAnnotationSubPanel.svelte
└── Icon.svelte
```

**survey/components/svelte/inline-panel/NumberResponseRenderer.svelte**

_No dependencies_

**process-diagram/components/process-diagram/svg-elems/Objects.svelte**

_No dependencies_

**report-grid/components/svelte/pickers/OrgUnitFieldPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
└── NoData.svelte
```

**playpen/3/builder/OverlayDiagramBuilder.svelte**

```
└── EntityGroupBox.svelte
    ├── GroupRow.svelte
    │   └── EntityCell.svelte
    │       ├── StatisticsBox.svelte
    │       └── CalloutBox.svelte
    └── CalloutBox.svelte
```

**aggregate-overlay-diagram/components/OverlayDiagramInstanceView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── AggregateOverlayDiagram.svelte
│   └── Callout.svelte
├── AggregateOverlayDiagramInstanceContextPanel.svelte
│   ├── CalloutList.svelte
│   │   ├── Icon.svelte
│   │   ├── Markdown.svelte
│   │   ├── NoData.svelte
│   │   ├── CalloutCreatePanel.svelte
│   │   │   ├── Icon.svelte
│   │   │   └── ColorPicker.svelte
│   │   └── CalloutDeletePanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── LastEdited.svelte
│   │   └── DateTime.svelte
│   ├── DescriptionFade.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── ImageDownloadLink.svelte
│       └── Icon.svelte
└── NoData.svelte
```

**aggregate-overlay-diagram/components/list-view/OverlayDiagramListView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── NoData.svelte
├── SearchInput.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/context-panel/OverlayDiagramPicker.svelte**

```
├── Icon.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/OverlayGlyph.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/OverlayGroupsPanel.svelte**

```
├── Icon.svelte
├── AddOverlayGroupEntrySubPanel.svelte
│   ├── GroupSelectorPanel.svelte
│   │   ├── EntitySearchSelector.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── MeasurableAlignmentTreeSelector.svelte
│   │       ├── SearchInput.svelte
│   │       │   └── Icon.svelte
│   │       └── MeasurableAlignmentTreeNode.svelte
│   │           └── Icon.svelte
│   ├── EntitySearchSelector.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── EditOverlayIconSubPanel.svelte
│       ├── Icon.svelte
│       ├── ColorPicker.svelte
│       ├── OverlayGlyph.svelte
│       ├── SymbolPicker.svelte
│       └── EntityLink.svelte
│           ├── ViewLink.svelte
│           └── EntityLabel.svelte
│               └── EntityIcon.svelte
│                   └── Icon.svelte
├── OverlayGlyph.svelte
├── EditOverlayIconSubPanel.svelte
│   ├── Icon.svelte
│   ├── ColorPicker.svelte
│   ├── OverlayGlyph.svelte
│   ├── SymbolPicker.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── CreateNewOverlayGroupPanel.svelte
│   └── Icon.svelte
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── ImportOverlayGroupSubPanel.svelte
    └── EntitySearchSelector.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**common/svelte/PageHeader.svelte**

```
└── Icon.svelte
```

**process-diagram/components/process-diagram/svg-elems/sub-types/Parallel.svelte**

_No dependencies_

**playpen/1/Parent.svelte**

```
└── Child.svelte
```

**user/svelte/PasswordUpdatePanel.svelte**

_No dependencies_

**system/svelte/permissions/PermissionsView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntityIcon.svelte
│   └── Icon.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/control/PersonEditor.svelte**

```
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**common/svelte/info-panels/PersonInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**common/svelte/PersonList.svelte**

```
├── Icon.svelte
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/PersonNavAid.svelte**

```
├── GroupLeader.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Group.svelte
    └── Unit.svelte
        └── GroupLeader.svelte
            └── EntityLabel.svelte
                └── EntityIcon.svelte
                    └── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/PersonNavAidBuilder.svelte**

```
├── BuilderControl.svelte
│   ├── NavTree.svelte
│   │   └── Icon.svelte
│   ├── RemovalConfirmation.svelte
│   ├── BlockEditor.svelte
│   └── PersonEditor.svelte
│       ├── EntitySearchSelector.svelte
│       │   └── EntityLabel.svelte
│       │       └── EntityIcon.svelte
│       │           └── Icon.svelte
│       └── Icon.svelte
├── PersonNavAid.svelte
│   ├── GroupLeader.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── Group.svelte
│       └── Unit.svelte
│           └── GroupLeader.svelte
│               └── EntityLabel.svelte
│                   └── EntityIcon.svelte
│                       └── Icon.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**common/svelte/entity-pickers/PersonPicker.svelte**

```
├── Icon.svelte
└── EntitySearchSelector.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/filters/PhysicalFlowAttributeFilters.svelte**

```
└── NoData.svelte
```

**physical-flows/svelte/PhysicalFlowCharacteristicsStep.svelte**

```
├── StepHeader.svelte
│   ├── Check.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── EnumSelect.svelte
│   ├── Icon.svelte
│   └── EnumHelpPanel.svelte
├── BasisOffsetSelect.svelte
├── Icon.svelte
└── Markdown.svelte
```

**data-flow/components/svelte/PhysicalFlowDetailTable.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── NoData.svelte
└── Icon.svelte
```

**physical-flows/svelte/PhysicalFlowRegistrationView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── LogicalFlowSelectionStep.svelte
│   ├── RouteSelector.svelte
│   │   ├── EntityLabel.svelte
│   │   │   └── EntityIcon.svelte
│   │   │       └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── SearchInput.svelte
│   │       └── Icon.svelte
│   ├── LogicalFlowLabel.svelte
│   │   ├── EntityLabel.svelte
│   │   │   └── EntityIcon.svelte
│   │   │       └── Icon.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   ├── StepHeader.svelte
│   │   ├── Check.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   └── FlowCreator.svelte
│       ├── Icon.svelte
│       ├── EntitySearchSelector.svelte
│       │   └── EntityLabel.svelte
│       │       └── EntityIcon.svelte
│       │           └── Icon.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── PhysicalFlowCharacteristicsStep.svelte
│   ├── StepHeader.svelte
│   │   ├── Check.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── EnumSelect.svelte
│   │   ├── Icon.svelte
│   │   └── EnumHelpPanel.svelte
│   ├── BasisOffsetSelect.svelte
│   ├── Icon.svelte
│   └── Markdown.svelte
├── PhysicalSpecificationStep.svelte
│   ├── StepHeader.svelte
│   │   ├── Check.svelte
│   │   │   └── Icon.svelte
│   │   └── Icon.svelte
│   ├── EnumSelect.svelte
│   │   ├── Icon.svelte
│   │   └── EnumHelpPanel.svelte
│   ├── PhysicalSpecificationSelector.svelte
│   │   └── SearchInput.svelte
│   │       └── Icon.svelte
│   └── Icon.svelte
├── Icon.svelte
├── ClonePhysicalFlowPanel.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── Toggle.svelte
│   └── Icon.svelte
└── DataTypeSelectionStep.svelte
    ├── StepHeader.svelte
    │   ├── Check.svelte
    │   │   └── Icon.svelte
    │   └── Icon.svelte
    ├── Icon.svelte
    ├── DataTypeTreeSelector.svelte
    │   ├── DataTypeTreeNode.svelte
    │   │   ├── Icon.svelte
    │   │   ├── RatingCharacteristicsDecorator.svelte
    │   │   │   └── FlowRatingCell.svelte
    │   │   ├── UsageCharacteristicsDecorator.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── DataTypeNodeTooltipContent.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   ├── RatingIndicatorCell.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── DescriptionFade.svelte
    │   │   │   │   ├── Markdown.svelte
    │   │   │   │   └── Icon.svelte
    │   │   │   ├── NoData.svelte
    │   │   │   └── Markdown.svelte
    │   │   └── Tooltip.svelte
    │   └── SearchInput.svelte
    │       └── Icon.svelte
    └── NoData.svelte
```

**data-flow/components/svelte/flow-detail-tab/PhysicalFlowTable.svelte**

```
└── SearchInput.svelte
    └── Icon.svelte
```

**physical-flows/svelte/PhysicalSpecificationSelector.svelte**

```
└── SearchInput.svelte
    └── Icon.svelte
```

**physical-flows/svelte/PhysicalSpecificationStep.svelte**

```
├── StepHeader.svelte
│   ├── Check.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── EnumSelect.svelte
│   ├── Icon.svelte
│   └── EnumHelpPanel.svelte
├── PhysicalSpecificationSelector.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
└── Icon.svelte
```

**common/svelte/popover/Popover.svelte**

```
└── PopoverContent.svelte
    └── Icon.svelte
```

**common/svelte/popover/PopoverContent.svelte**

```
└── Icon.svelte
```

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/presets/PresetSelector.svelte**

```
├── NoData.svelte
└── Markdown.svelte
```

**measurable-rating/svelte/PrimaryRatingOverviewSubSection.svelte**

```
├── SubSection.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── Icon.svelte
├── Tooltip.svelte
└── PrimaryRatingTooltipContent.svelte
    ├── DescriptionFade.svelte
    │   ├── Markdown.svelte
    │   └── Icon.svelte
    └── RatingIndicatorCell.svelte
        └── Icon.svelte
```

**measurable-rating/svelte/PrimaryRatingTooltipContent.svelte**

```
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── RatingIndicatorCell.svelte
    └── Icon.svelte
```

**process-diagram/components/process-diagram/ProcessDiagram.svelte**

```
├── Defs.svelte
├── Objects.svelte
├── Connections.svelte
├── ProcessDiagramContextPanel.svelte
│   ├── ActivityContextPanel.svelte
│   │   ├── EntityInfoPanel.svelte
│   │   │   ├── ApplicationInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── ChangeInitiativeInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── MeasurableInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── ActorInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── DataTypeInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   │   └── DescriptionFade.svelte
│   │   │   │       ├── Markdown.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── LogicalDataFlowInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   │   └── EntityLink.svelte
│   │   │   │   │       ├── ViewLink.svelte
│   │   │   │   │       └── EntityLabel.svelte
│   │   │   │   │           └── EntityIcon.svelte
│   │   │   │   │               └── Icon.svelte
│   │   │   │   └── KeyAssessmentInfoPanel.svelte
│   │   │   ├── SurveyInstanceInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   ├── DateTime.svelte
│   │   │   │   ├── DescriptionFade.svelte
│   │   │   │   │   ├── Markdown.svelte
│   │   │   │   │   └── Icon.svelte
│   │   │   │   └── DatePicker.svelte
│   │   │   │       └── Icon.svelte
│   │   │   ├── PersonInfoPanel.svelte
│   │   │   │   ├── EntityLink.svelte
│   │   │   │   │   ├── ViewLink.svelte
│   │   │   │   │   └── EntityLabel.svelte
│   │   │   │   │       └── EntityIcon.svelte
│   │   │   │   │           └── Icon.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── EndUserApplicationInfoPanel.svelte
│   │   │       └── DescriptionFade.svelte
│   │   │           ├── Markdown.svelte
│   │   │           └── Icon.svelte
│   │   └── ConnectionsSubContextPanel.svelte
│   ├── DecisionContextPanel.svelte
│   │   └── ConnectionsSubContextPanel.svelte
│   ├── DefaultContextPanel.svelte
│   │   ├── Icon.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   └── SearchInput.svelte
│   │       └── Icon.svelte
│   ├── ApplicationContextPanel.svelte
│   │   └── EntityInfoPanel.svelte
│   │       ├── ApplicationInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── KeyInvolvementInfoPanel.svelte
│   │       │   │   └── EntityLink.svelte
│   │       │   │       ├── ViewLink.svelte
│   │       │   │       └── EntityLabel.svelte
│   │       │   │           └── EntityIcon.svelte
│   │       │   │               └── Icon.svelte
│   │       │   ├── KeyAssessmentInfoPanel.svelte
│   │       │   └── DescriptionFade.svelte
│   │       │       ├── Markdown.svelte
│   │       │       └── Icon.svelte
│   │       ├── ChangeInitiativeInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── KeyInvolvementInfoPanel.svelte
│   │       │   │   └── EntityLink.svelte
│   │       │   │       ├── ViewLink.svelte
│   │       │   │       └── EntityLabel.svelte
│   │       │   │           └── EntityIcon.svelte
│   │       │   │               └── Icon.svelte
│   │       │   ├── KeyAssessmentInfoPanel.svelte
│   │       │   └── DescriptionFade.svelte
│   │       │       ├── Markdown.svelte
│   │       │       └── Icon.svelte
│   │       ├── MeasurableInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── KeyInvolvementInfoPanel.svelte
│   │       │   │   └── EntityLink.svelte
│   │       │   │       ├── ViewLink.svelte
│   │       │   │       └── EntityLabel.svelte
│   │       │   │           └── EntityIcon.svelte
│   │       │   │               └── Icon.svelte
│   │       │   ├── KeyAssessmentInfoPanel.svelte
│   │       │   └── DescriptionFade.svelte
│   │       │       ├── Markdown.svelte
│   │       │       └── Icon.svelte
│   │       ├── ActorInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── KeyInvolvementInfoPanel.svelte
│   │       │   │   └── EntityLink.svelte
│   │       │   │       ├── ViewLink.svelte
│   │       │   │       └── EntityLabel.svelte
│   │       │   │           └── EntityIcon.svelte
│   │       │   │               └── Icon.svelte
│   │       │   ├── KeyAssessmentInfoPanel.svelte
│   │       │   └── DescriptionFade.svelte
│   │       │       ├── Markdown.svelte
│   │       │       └── Icon.svelte
│   │       ├── DataTypeInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── KeyInvolvementInfoPanel.svelte
│   │       │   │   └── EntityLink.svelte
│   │       │   │       ├── ViewLink.svelte
│   │       │   │       └── EntityLabel.svelte
│   │       │   │           └── EntityIcon.svelte
│   │       │   │               └── Icon.svelte
│   │       │   ├── KeyAssessmentInfoPanel.svelte
│   │       │   └── DescriptionFade.svelte
│   │       │       ├── Markdown.svelte
│   │       │       └── Icon.svelte
│   │       ├── LogicalDataFlowInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── KeyInvolvementInfoPanel.svelte
│   │       │   │   └── EntityLink.svelte
│   │       │   │       ├── ViewLink.svelte
│   │       │   │       └── EntityLabel.svelte
│   │       │   │           └── EntityIcon.svelte
│   │       │   │               └── Icon.svelte
│   │       │   └── KeyAssessmentInfoPanel.svelte
│   │       ├── SurveyInstanceInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   ├── DateTime.svelte
│   │       │   ├── DescriptionFade.svelte
│   │       │   │   ├── Markdown.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── DatePicker.svelte
│   │       │       └── Icon.svelte
│   │       ├── PersonInfoPanel.svelte
│   │       │   ├── EntityLink.svelte
│   │       │   │   ├── ViewLink.svelte
│   │       │   │   └── EntityLabel.svelte
│   │       │   │       └── EntityIcon.svelte
│   │       │   │           └── Icon.svelte
│   │       │   └── Icon.svelte
│   │       └── EndUserApplicationInfoPanel.svelte
│   │           └── DescriptionFade.svelte
│   │               ├── Markdown.svelte
│   │               └── Icon.svelte
│   ├── EventContextPanel.svelte
│   │   └── ConnectionsSubContextPanel.svelte
│   ├── NavigationCellContextPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── Markdown.svelte
│   ├── Activity.svelte
│   └── Decision.svelte
└── ImageDownloadLink.svelte
    └── Icon.svelte
```

**process-diagram/components/process-diagram/context-panels/ProcessDiagramContextPanel.svelte**

```
├── ActivityContextPanel.svelte
│   ├── EntityInfoPanel.svelte
│   │   ├── ApplicationInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── ChangeInitiativeInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── MeasurableInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── ActorInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── DataTypeInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   ├── KeyAssessmentInfoPanel.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   ├── LogicalDataFlowInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── KeyInvolvementInfoPanel.svelte
│   │   │   │   └── EntityLink.svelte
│   │   │   │       ├── ViewLink.svelte
│   │   │   │       └── EntityLabel.svelte
│   │   │   │           └── EntityIcon.svelte
│   │   │   │               └── Icon.svelte
│   │   │   └── KeyAssessmentInfoPanel.svelte
│   │   ├── SurveyInstanceInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   ├── DateTime.svelte
│   │   │   ├── DescriptionFade.svelte
│   │   │   │   ├── Markdown.svelte
│   │   │   │   └── Icon.svelte
│   │   │   └── DatePicker.svelte
│   │   │       └── Icon.svelte
│   │   ├── PersonInfoPanel.svelte
│   │   │   ├── EntityLink.svelte
│   │   │   │   ├── ViewLink.svelte
│   │   │   │   └── EntityLabel.svelte
│   │   │   │       └── EntityIcon.svelte
│   │   │   │           └── Icon.svelte
│   │   │   └── Icon.svelte
│   │   └── EndUserApplicationInfoPanel.svelte
│   │       └── DescriptionFade.svelte
│   │           ├── Markdown.svelte
│   │           └── Icon.svelte
│   └── ConnectionsSubContextPanel.svelte
├── DecisionContextPanel.svelte
│   └── ConnectionsSubContextPanel.svelte
├── DefaultContextPanel.svelte
│   ├── Icon.svelte
│   ├── DescriptionFade.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── ApplicationContextPanel.svelte
│   └── EntityInfoPanel.svelte
│       ├── ApplicationInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── KeyInvolvementInfoPanel.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── KeyAssessmentInfoPanel.svelte
│       │   └── DescriptionFade.svelte
│       │       ├── Markdown.svelte
│       │       └── Icon.svelte
│       ├── ChangeInitiativeInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── KeyInvolvementInfoPanel.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── KeyAssessmentInfoPanel.svelte
│       │   └── DescriptionFade.svelte
│       │       ├── Markdown.svelte
│       │       └── Icon.svelte
│       ├── MeasurableInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── KeyInvolvementInfoPanel.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── KeyAssessmentInfoPanel.svelte
│       │   └── DescriptionFade.svelte
│       │       ├── Markdown.svelte
│       │       └── Icon.svelte
│       ├── ActorInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── KeyInvolvementInfoPanel.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── KeyAssessmentInfoPanel.svelte
│       │   └── DescriptionFade.svelte
│       │       ├── Markdown.svelte
│       │       └── Icon.svelte
│       ├── DataTypeInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── KeyInvolvementInfoPanel.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── KeyAssessmentInfoPanel.svelte
│       │   └── DescriptionFade.svelte
│       │       ├── Markdown.svelte
│       │       └── Icon.svelte
│       ├── LogicalDataFlowInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── KeyInvolvementInfoPanel.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   └── KeyAssessmentInfoPanel.svelte
│       ├── SurveyInstanceInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   ├── DateTime.svelte
│       │   ├── DescriptionFade.svelte
│       │   │   ├── Markdown.svelte
│       │   │   └── Icon.svelte
│       │   └── DatePicker.svelte
│       │       └── Icon.svelte
│       ├── PersonInfoPanel.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   └── Icon.svelte
│       └── EndUserApplicationInfoPanel.svelte
│           └── DescriptionFade.svelte
│               ├── Markdown.svelte
│               └── Icon.svelte
├── EventContextPanel.svelte
│   └── ConnectionsSubContextPanel.svelte
├── NavigationCellContextPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── Markdown.svelte
├── Activity.svelte
└── Decision.svelte
```

**assessments/components/rating-editor/RatingAddView.svelte**

```
├── Icon.svelte
└── Markdown.svelte
```

**common/svelte/RatingCharacteristicsDecorator.svelte**

```
└── FlowRatingCell.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/cost/RatingCostOverlay.svelte**

```
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/overlays/cost/RatingCostOverlayParameters.svelte**

```
└── CostKindPicker.svelte
    ├── Grid.svelte
    │   └── Icon.svelte
    └── Icon.svelte
```

**assessments/components/rating-editor/RatingDetailView.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
├── LastEdited.svelte
│   └── DateTime.svelte
├── TextEditableField.svelte
│   ├── Markdown.svelte
│   ├── SavingPlaceholder.svelte
│   │   └── Icon.svelte
│   └── Icon.svelte
├── Icon.svelte
├── EditableRatingValue.svelte
│   ├── Icon.svelte
│   ├── SavingPlaceholder.svelte
│   │   └── Icon.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── RatingItemDropdownPicker.svelte
└── Markdown.svelte
```

**ratings/components/rating-indicator-cell/RatingIndicatorCell.svelte**

```
└── Icon.svelte
```

**assessments/components/rating-editor/RatingItemDropdownPicker.svelte**

_No dependencies_

**assessments/components/rating-editor/RatingListView.svelte**

```
├── NoData.svelte
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**common/svelte/RatingPicker.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**system/svelte/ratings-schemes/RatingSchemesAdminView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── ItemPreviewBar.svelte
├── SchemeEditor.svelte
│   └── Icon.svelte
├── ItemsView.svelte
│   ├── Icon.svelte
│   ├── ItemEditor.svelte
│   │   ├── ColorPicker.svelte
│   │   └── Icon.svelte
│   └── ItemRemovalConfirmation.svelte
│       └── Icon.svelte
├── Icon.svelte
└── SchemeRemovalConfirmation.svelte
```

**system/svelte/recipients/ReassignRecipientsView.svelte**

```
├── ViewLink.svelte
└── PageHeader.svelte
    └── Icon.svelte
```

**data-types/components/related-data-types-section/RelatedDataTypesSection.svelte**

```
├── DataTypeTreeNode.svelte
│   ├── Icon.svelte
│   ├── RatingCharacteristicsDecorator.svelte
│   │   └── FlowRatingCell.svelte
│   ├── UsageCharacteristicsDecorator.svelte
│   │   └── Icon.svelte
│   ├── DataTypeNodeTooltipContent.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   ├── NoData.svelte
│   │   └── Markdown.svelte
│   └── Tooltip.svelte
├── DataTypeTreeSelector.svelte
│   ├── DataTypeTreeNode.svelte
│   │   ├── Icon.svelte
│   │   ├── RatingCharacteristicsDecorator.svelte
│   │   │   └── FlowRatingCell.svelte
│   │   ├── UsageCharacteristicsDecorator.svelte
│   │   │   └── Icon.svelte
│   │   ├── DataTypeNodeTooltipContent.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── RatingIndicatorCell.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── DescriptionFade.svelte
│   │   │   │   ├── Markdown.svelte
│   │   │   │   └── Icon.svelte
│   │   │   ├── NoData.svelte
│   │   │   └── Markdown.svelte
│   │   └── Tooltip.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── NoData.svelte
├── Markdown.svelte
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── SubSection.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/RelatedEntitiesPanel.svelte**

```
├── RelatedEntitiesViewTable.svelte
│   ├── Icon.svelte
│   ├── RelatedEntityAddNodesPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   └── Icon.svelte
│   ├── RelatedEntityAddFlowsPanel.svelte
│   │   └── Icon.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── AddRelatedMeasurableSubPanel.svelte
│   ├── Icon.svelte
│   └── EntitySearchSelector.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── AddRelatedChangeInitiativeSubPanel.svelte
│   └── EntitySearchSelector.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
└── AddRelatedDataTypeSubPanel.svelte
    └── EntitySearchSelector.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/RelatedEntitiesViewTable.svelte**

```
├── Icon.svelte
├── RelatedEntityAddNodesPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── Icon.svelte
├── RelatedEntityAddFlowsPanel.svelte
│   └── Icon.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/RelatedEntityAddFlowsPanel.svelte**

```
└── Icon.svelte
```

**flow-diagram/components/diagram-svelte/context-panel/RelatedEntityAddNodesPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**system/svelte/nav-aid-builder/custom/control/RemovalConfirmation.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/RemoveDiagramSubPanel.svelte**

```
└── Icon.svelte
```

**system/svelte/licences/RemoveLicencePanel.svelte**

_No dependencies_

**assessments/components/rating-editor/RemoveRatingConfirmationPanel.svelte**

```
├── RatingIndicatorCell.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**measurable/components/change-control/ReorderMeasurables.svelte**

```
└── SortableList.svelte
```

**measurable-rating/svelte/ReplacementAppMiniTable.svelte**

```
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**report-grid/components/svelte/ReportGridCloneConfirmation.svelte**

_No dependencies_

**report-grid/components/svelte/column-definition-edit-panel/ReportGridColumnSummary.svelte**

```
├── NoData.svelte
└── Icon.svelte
```

**report-grid/components/svelte/ReportGridControlPanel.svelte**

```
├── ReportGridOverview.svelte
│   ├── ReportGridPicker.svelte
│   │   ├── NoData.svelte
│   │   ├── Icon.svelte
│   │   └── SearchInput.svelte
│   │       └── Icon.svelte
│   ├── NoData.svelte
│   ├── ReportGridEditor.svelte
│   ├── ReportGridCloneConfirmation.svelte
│   └── Icon.svelte
├── ReportGridFilters.svelte
│   ├── NoData.svelte
│   ├── Icon.svelte
│   ├── EntityIcon.svelte
│   │   └── Icon.svelte
│   └── FilterNotePopoverContent.svelte
│       ├── Markdown.svelte
│       └── Icon.svelte
├── ColumnDefinitionEditPanel.svelte
│   ├── EntitySelector.svelte
│   │   ├── Icon.svelte
│   │   └── EntityPicker.svelte
│   │       ├── InvolvementPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── Icon.svelte
│   │       ├── CostKindPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── Icon.svelte
│   │       ├── SurveyQuestionPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── Toggle.svelte
│   │       │       └── Icon.svelte
│   │       ├── AssessmentDefinitionPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── Icon.svelte
│   │       ├── PersonPicker.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── EntitySearchSelector.svelte
│   │       │       └── EntityLabel.svelte
│   │       │           └── EntityIcon.svelte
│   │       │               └── Icon.svelte
│   │       ├── SurveyInstanceFieldPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── Icon.svelte
│   │       │   ├── NoData.svelte
│   │       │   └── Toggle.svelte
│   │       │       └── Icon.svelte
│   │       ├── ApplicationFieldPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── NoData.svelte
│   │       ├── ChangeInitiativeFieldPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── NoData.svelte
│   │       ├── AppGroupPicker.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── EntitySearchSelector.svelte
│   │       │       └── EntityLabel.svelte
│   │       │           └── EntityIcon.svelte
│   │       │               └── Icon.svelte
│   │       ├── DataTypePicker.svelte
│   │       │   └── DataTypeTreeSelector.svelte
│   │       │       ├── DataTypeTreeNode.svelte
│   │       │       │   ├── Icon.svelte
│   │       │       │   ├── RatingCharacteristicsDecorator.svelte
│   │       │       │   │   └── FlowRatingCell.svelte
│   │       │       │   ├── UsageCharacteristicsDecorator.svelte
│   │       │       │   │   └── Icon.svelte
│   │       │       │   ├── DataTypeNodeTooltipContent.svelte
│   │       │       │   │   ├── Icon.svelte
│   │       │       │   │   ├── RatingIndicatorCell.svelte
│   │       │       │   │   │   └── Icon.svelte
│   │       │       │   │   ├── DescriptionFade.svelte
│   │       │       │   │   │   ├── Markdown.svelte
│   │       │       │   │   │   └── Icon.svelte
│   │       │       │   │   ├── NoData.svelte
│   │       │       │   │   └── Markdown.svelte
│   │       │       │   └── Tooltip.svelte
│   │       │       └── SearchInput.svelte
│   │       │           └── Icon.svelte
│   │       ├── AttestationPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── Icon.svelte
│   │       ├── OrgUnitFieldPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── NoData.svelte
│   │       ├── TagPicker.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── NoData.svelte
│   │       ├── AliasPicker.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── NoData.svelte
│   │       ├── ComplexityKindPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   └── Icon.svelte
│   │       ├── MeasurableCategoryPicker.svelte
│   │       │   ├── Grid.svelte
│   │       │   │   └── Icon.svelte
│   │       │   ├── Icon.svelte
│   │       │   └── MeasurableTreeSelector.svelte
│   │       │       ├── MeasurableTreeNode.svelte
│   │       │       │   └── Icon.svelte
│   │       │       └── SearchInput.svelte
│   │       │           └── Icon.svelte
│   │       ├── EntityStatisticPicker.svelte
│   │       │   └── EntityStatisticTreeSelector.svelte
│   │       │       ├── SearchInput.svelte
│   │       │       │   └── Icon.svelte
│   │       │       └── StatisticTreeNode.svelte
│   │       │           └── Icon.svelte
│   │       └── MeasurablePicker.svelte
│   │           ├── Icon.svelte
│   │           ├── MeasurableTreeSelector.svelte
│   │           │   ├── MeasurableTreeNode.svelte
│   │           │   │   └── Icon.svelte
│   │           │   └── SearchInput.svelte
│   │           │       └── Icon.svelte
│   │           ├── Grid.svelte
│   │           │   └── Icon.svelte
│   │           └── NoData.svelte
│   ├── ReportGridColumnSummary.svelte
│   │   ├── NoData.svelte
│   │   └── Icon.svelte
│   ├── Icon.svelte
│   ├── FixedColumnDetailsEditor.svelte
│   │   ├── DropdownPicker.svelte
│   │   │   └── Icon.svelte
│   │   ├── Icon.svelte
│   │   └── ColumnDefinitionHeader.svelte
│   │       └── DescriptionFade.svelte
│   │           ├── Markdown.svelte
│   │           └── Icon.svelte
│   ├── DerivedColumnDetailsEditor.svelte
│   │   ├── Icon.svelte
│   │   ├── ColumnDefinitionHeader.svelte
│   │   │   └── DescriptionFade.svelte
│   │   │       ├── Markdown.svelte
│   │   │       └── Icon.svelte
│   │   └── Markdown.svelte
│   ├── NoData.svelte
│   ├── ColumnRemovalConfirmation.svelte
│   └── Markdown.svelte
├── Icon.svelte
└── ReportGridPersonEditPanel.svelte
    ├── Icon.svelte
    ├── SearchInput.svelte
    │   └── Icon.svelte
    ├── AddNewSubscriberPanel.svelte
    │   ├── Icon.svelte
    │   └── EntitySearchSelector.svelte
    │       └── EntityLabel.svelte
    │           └── EntityIcon.svelte
    │               └── Icon.svelte
    └── ReportGridPersonOverview.svelte
        ├── Icon.svelte
        └── PersonInfoPanel.svelte
            ├── EntityLink.svelte
            │   ├── ViewLink.svelte
            │   └── EntityLabel.svelte
            │       └── EntityIcon.svelte
            │           └── Icon.svelte
            └── Icon.svelte
```

**report-grid/components/svelte/ReportGridEditor.svelte**

_No dependencies_

**report-grid/components/svelte/ReportGridFilters.svelte**

```
├── NoData.svelte
├── Icon.svelte
├── EntityIcon.svelte
│   └── Icon.svelte
└── FilterNotePopoverContent.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**report-grid/components/svelte/ReportGridOverview.svelte**

```
├── ReportGridPicker.svelte
│   ├── NoData.svelte
│   ├── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
├── NoData.svelte
├── ReportGridEditor.svelte
├── ReportGridCloneConfirmation.svelte
└── Icon.svelte
```

**report-grid/components/svelte/ReportGridPageHeader.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**report-grid/components/svelte/person-edit-panel/ReportGridPersonEditPanel.svelte**

```
├── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── AddNewSubscriberPanel.svelte
│   ├── Icon.svelte
│   └── EntitySearchSelector.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
└── ReportGridPersonOverview.svelte
    ├── Icon.svelte
    └── PersonInfoPanel.svelte
        ├── EntityLink.svelte
        │   ├── ViewLink.svelte
        │   └── EntityLabel.svelte
        │       └── EntityIcon.svelte
        │           └── Icon.svelte
        └── Icon.svelte
```

**report-grid/components/svelte/person-edit-panel/ReportGridPersonOverview.svelte**

```
├── Icon.svelte
└── PersonInfoPanel.svelte
    ├── EntityLink.svelte
    │   ├── ViewLink.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    └── Icon.svelte
```

**report-grid/components/svelte/ReportGridPicker.svelte**

```
├── NoData.svelte
├── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**legal-entity-relationship-kind/components/bulk-upload/ResolvedUploadReport.svelte**

```
├── Icon.svelte
├── Tooltip.svelte
├── RowErrorTooltip.svelte
└── AssessmentErrorTooltip.svelte
```

**role/svelte/RoleList.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── Icon.svelte
└── ViewLink.svelte
```

**role/svelte/RoleListPanel.svelte**

```
├── Icon.svelte
└── RoleList.svelte
    ├── SearchInput.svelte
    │   └── Icon.svelte
    ├── Icon.svelte
    └── ViewLink.svelte
```

**role/svelte/RoleView.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**physical-flows/svelte/RouteSelector.svelte**

```
├── EntityLabel.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
├── Icon.svelte
└── SearchInput.svelte
    └── Icon.svelte
```

**legal-entity-relationship-kind/components/bulk-upload/RowErrorTooltip.svelte**

_No dependencies_

**legal-entity-relationship-kind/components/bulk-upload/SaveUploadReport.svelte**

```
└── Icon.svelte
```

**common/svelte/SavingPlaceholder.svelte**

```
└── Icon.svelte
```

**system/svelte/ratings-schemes/SchemeEditor.svelte**

```
└── Icon.svelte
```

**system/svelte/ratings-schemes/SchemeRemovalConfirmation.svelte**

_No dependencies_

**common/svelte/SearchInput.svelte**

```
└── Icon.svelte
```

**common/svelte/Section.svelte**

```
└── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/SelectedFlowDetailPanel.svelte**

```
├── Icon.svelte
├── SelectedLogicalFlowDetail.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── Icon.svelte
│   ├── DataTypeMiniTable.svelte
│   │   ├── EntityLabel.svelte
│   │   │   └── EntityIcon.svelte
│   │   │       └── Icon.svelte
│   │   ├── Toggle.svelte
│   │   │   └── Icon.svelte
│   │   ├── FlowRatingCell.svelte
│   │   └── RatingIndicatorCell.svelte
│   │       └── Icon.svelte
│   ├── AssessmentsTable.svelte
│   │   ├── RatingIndicatorCell.svelte
│   │   │   └── Icon.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── SelectedPhysicalFlowDetail.svelte
    ├── EntityLink.svelte
    │   ├── ViewLink.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    ├── Icon.svelte
    ├── DescriptionFade.svelte
    │   ├── Markdown.svelte
    │   └── Icon.svelte
    ├── DataTypeMiniTable.svelte
    │   ├── EntityLabel.svelte
    │   │   └── EntityIcon.svelte
    │   │       └── Icon.svelte
    │   ├── Toggle.svelte
    │   │   └── Icon.svelte
    │   ├── FlowRatingCell.svelte
    │   └── RatingIndicatorCell.svelte
    │       └── Icon.svelte
    ├── AssessmentsTable.svelte
    │   ├── RatingIndicatorCell.svelte
    │   │   └── Icon.svelte
    │   └── EntityIcon.svelte
    │       └── Icon.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/context-panel/SelectedGroupPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── CellContent.svelte
└── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/SelectedLogicalFlowDetail.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── Icon.svelte
├── DataTypeMiniTable.svelte
│   ├── EntityLabel.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   ├── Toggle.svelte
│   │   └── Icon.svelte
│   ├── FlowRatingCell.svelte
│   └── RatingIndicatorCell.svelte
│       └── Icon.svelte
├── AssessmentsTable.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**data-flow/components/svelte/flow-detail-tab/SelectedPhysicalFlowDetail.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── Icon.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
├── DataTypeMiniTable.svelte
│   ├── EntityLabel.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   ├── Toggle.svelte
│   │   └── Icon.svelte
│   ├── FlowRatingCell.svelte
│   └── RatingIndicatorCell.svelte
│       └── Icon.svelte
├── AssessmentsTable.svelte
│   ├── RatingIndicatorCell.svelte
│   │   └── Icon.svelte
│   └── EntityIcon.svelte
│       └── Icon.svelte
└── EntityLabel.svelte
    └── EntityIcon.svelte
        └── Icon.svelte
```

**role/svelte/SelectedRolePanel.svelte**

```
├── Icon.svelte
└── ViewLink.svelte
```

**system/svelte/settings/SettingsAdminView.svelte**

```
├── NoData.svelte
├── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
├── Section.svelte
│   └── Icon.svelte
└── CreateSettingPanel.svelte
```

**navbar/Sidebar.svelte**

```
└── Icon.svelte
```

**logical-flow/svelte/flow-venn/SingleVenn.svelte**

_No dependencies_

**measurable/components/change-control/SortableList.svelte**

_No dependencies_

**data-flow/components/svelte/flow-detail-tab/filters/SourceTargetKindFilters.svelte**

```
└── Icon.svelte
```

**system/svelte/static-diagrams/StaticDiagram.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
└── ViewLink.svelte
```

**system/svelte/static-diagrams/StaticDiagramsAdminView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── StaticDiagram.svelte
│   ├── PageHeader.svelte
│   │   └── Icon.svelte
│   └── ViewLink.svelte
└── Icon.svelte
```

**common/svelte/StaticPanel.svelte**

```
├── Markdown.svelte
└── Icon.svelte
```

**system/svelte/static-panels/StaticPanelEditor.svelte**

```
└── Icon.svelte
```

**common/svelte/StaticPanels.svelte**

```
└── StaticPanel.svelte
    ├── Markdown.svelte
    └── Icon.svelte
```

**system/svelte/static-panels/StaticPanelsAdminView.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
├── Icon.svelte
├── StaticPanelEditor.svelte
│   └── Icon.svelte
└── ViewLink.svelte
```

**playpen/3/builder/StatisticsBox.svelte**

_No dependencies_

**common/svelte/StatisticTreeNode.svelte**

```
└── Icon.svelte
```

**physical-flows/svelte/StepHeader.svelte**

```
├── Check.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**survey/components/string-list-input/StringListInput.svelte**

```
└── Icon.svelte
```

**survey/components/svelte/inline-panel/StringResponseRenderer.svelte**

```
└── Markdown.svelte
```

**common/svelte/SubSection.svelte**

_No dependencies_

**data-types/components/data-type-decorator-section/SuggestedDataTypeTreeSelector.svelte**

```
└── DataTypeTreeSelector.svelte
    ├── DataTypeTreeNode.svelte
    │   ├── Icon.svelte
    │   ├── RatingCharacteristicsDecorator.svelte
    │   │   └── FlowRatingCell.svelte
    │   ├── UsageCharacteristicsDecorator.svelte
    │   │   └── Icon.svelte
    │   ├── DataTypeNodeTooltipContent.svelte
    │   │   ├── Icon.svelte
    │   │   ├── RatingIndicatorCell.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   └── Markdown.svelte
    │   └── Tooltip.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

**survey/components/svelte/inline-panel/SurveyActions.svelte**

```
├── Icon.svelte
├── NoData.svelte
├── ViewLink.svelte
└── StaticPanels.svelte
    └── StaticPanel.svelte
        ├── Markdown.svelte
        └── Icon.svelte
```

**survey/components/svelte/SurveyApproverInfoPanel.svelte**

```
├── SurveyInstanceGrid.svelte
│   └── Icon.svelte
├── NoData.svelte
├── Icon.svelte
└── SurveyViewer.svelte
    ├── SurveyQuestionResponse.svelte
    │   ├── StringResponseRenderer.svelte
    │   │   └── Markdown.svelte
    │   ├── NumberResponseRenderer.svelte
    │   ├── DateResponseRenderer.svelte
    │   ├── EntityResponseRenderer.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── BooleanResponseRenderer.svelte
    │   │   └── Icon.svelte
    │   ├── DropdownMultiResponseRenderer.svelte
    │   ├── MeasurableMultiResponseRenderer.svelte
    │   │   └── MeasurableTree.svelte
    │   │       └── EntityLink.svelte
    │   │           ├── ViewLink.svelte
    │   │           └── EntityLabel.svelte
    │   │               └── EntityIcon.svelte
    │   │                   └── Icon.svelte
    │   └── LegalEntityResponseRenderer.svelte
    │       └── EntityLink.svelte
    │           ├── ViewLink.svelte
    │           └── EntityLabel.svelte
    │               └── EntityIcon.svelte
    │                   └── Icon.svelte
    ├── SurveyViewerContextPanel.svelte
    │   ├── Icon.svelte
    │   ├── SurveyInstanceInfoPanel.svelte
    │   │   ├── EntityLink.svelte
    │   │   │   ├── ViewLink.svelte
    │   │   │   └── EntityLabel.svelte
    │   │   │       └── EntityIcon.svelte
    │   │   │           └── Icon.svelte
    │   │   ├── DateTime.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   └── DatePicker.svelte
    │   │       └── Icon.svelte
    │   ├── SurveyPeople.svelte
    │   │   ├── PersonList.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   ├── EntityLabel.svelte
    │   │   │   │   └── EntityIcon.svelte
    │   │   │   │       └── Icon.svelte
    │   │   │   └── EntitySearchSelector.svelte
    │   │   │       └── EntityLabel.svelte
    │   │   │           └── EntityIcon.svelte
    │   │   │               └── Icon.svelte
    │   │   └── Icon.svelte
    │   ├── SurveyActions.svelte
    │   │   ├── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── StaticPanels.svelte
    │   │       └── StaticPanel.svelte
    │   │           ├── Markdown.svelte
    │   │           └── Icon.svelte
    │   ├── SurveyInstanceVersionPicker.svelte
    │   │   └── Icon.svelte
    │   └── DataExtractLink.svelte
    │       └── Icon.svelte
    ├── CopySurveyResponsesPanel.svelte
    │   ├── NoData.svelte
    │   ├── Icon.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   └── SearchInput.svelte
    │       └── Icon.svelte
    └── Markdown.svelte
```

**report-grid/components/svelte/pickers/SurveyInstanceFieldPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
├── NoData.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**survey/components/svelte/SurveyInstanceGrid.svelte**

```
└── Icon.svelte
```

**common/svelte/info-panels/SurveyInstanceInfoPanel.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── DateTime.svelte
├── DescriptionFade.svelte
│   ├── Markdown.svelte
│   └── Icon.svelte
└── DatePicker.svelte
    └── Icon.svelte
```

**survey/components/svelte/SurveyInstanceList.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── DateTime.svelte
├── Toggle.svelte
│   └── Icon.svelte
├── DatePicker.svelte
│   └── Icon.svelte
└── NoData.svelte
```

**survey/components/svelte/inline-panel/SurveyInstanceVersionPicker.svelte**

```
└── Icon.svelte
```

**survey/components/svelte/inline-panel/SurveyPeople.svelte**

```
├── PersonList.svelte
│   ├── Icon.svelte
│   ├── EntityLabel.svelte
│   │   └── EntityIcon.svelte
│   │       └── Icon.svelte
│   └── EntitySearchSelector.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
└── Icon.svelte
```

**report-grid/components/svelte/pickers/SurveyQuestionPicker.svelte**

```
├── Grid.svelte
│   └── Icon.svelte
├── Icon.svelte
└── Toggle.svelte
    └── Icon.svelte
```

**survey/components/svelte/inline-panel/SurveyQuestionResponse.svelte**

```
├── StringResponseRenderer.svelte
│   └── Markdown.svelte
├── NumberResponseRenderer.svelte
├── DateResponseRenderer.svelte
├── EntityResponseRenderer.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── BooleanResponseRenderer.svelte
│   └── Icon.svelte
├── DropdownMultiResponseRenderer.svelte
├── MeasurableMultiResponseRenderer.svelte
│   └── MeasurableTree.svelte
│       └── EntityLink.svelte
│           ├── ViewLink.svelte
│           └── EntityLabel.svelte
│               └── EntityIcon.svelte
│                   └── Icon.svelte
└── LegalEntityResponseRenderer.svelte
    └── EntityLink.svelte
        ├── ViewLink.svelte
        └── EntityLabel.svelte
            └── EntityIcon.svelte
                └── Icon.svelte
```

**survey/components/svelte/SurveyRecipientInfoPanel.svelte**

```
├── SurveyInstanceGrid.svelte
│   └── Icon.svelte
├── NoData.svelte
├── Icon.svelte
└── SurveyViewer.svelte
    ├── SurveyQuestionResponse.svelte
    │   ├── StringResponseRenderer.svelte
    │   │   └── Markdown.svelte
    │   ├── NumberResponseRenderer.svelte
    │   ├── DateResponseRenderer.svelte
    │   ├── EntityResponseRenderer.svelte
    │   │   └── EntityLink.svelte
    │   │       ├── ViewLink.svelte
    │   │       └── EntityLabel.svelte
    │   │           └── EntityIcon.svelte
    │   │               └── Icon.svelte
    │   ├── BooleanResponseRenderer.svelte
    │   │   └── Icon.svelte
    │   ├── DropdownMultiResponseRenderer.svelte
    │   ├── MeasurableMultiResponseRenderer.svelte
    │   │   └── MeasurableTree.svelte
    │   │       └── EntityLink.svelte
    │   │           ├── ViewLink.svelte
    │   │           └── EntityLabel.svelte
    │   │               └── EntityIcon.svelte
    │   │                   └── Icon.svelte
    │   └── LegalEntityResponseRenderer.svelte
    │       └── EntityLink.svelte
    │           ├── ViewLink.svelte
    │           └── EntityLabel.svelte
    │               └── EntityIcon.svelte
    │                   └── Icon.svelte
    ├── SurveyViewerContextPanel.svelte
    │   ├── Icon.svelte
    │   ├── SurveyInstanceInfoPanel.svelte
    │   │   ├── EntityLink.svelte
    │   │   │   ├── ViewLink.svelte
    │   │   │   └── EntityLabel.svelte
    │   │   │       └── EntityIcon.svelte
    │   │   │           └── Icon.svelte
    │   │   ├── DateTime.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   └── DatePicker.svelte
    │   │       └── Icon.svelte
    │   ├── SurveyPeople.svelte
    │   │   ├── PersonList.svelte
    │   │   │   ├── Icon.svelte
    │   │   │   ├── EntityLabel.svelte
    │   │   │   │   └── EntityIcon.svelte
    │   │   │   │       └── Icon.svelte
    │   │   │   └── EntitySearchSelector.svelte
    │   │   │       └── EntityLabel.svelte
    │   │   │           └── EntityIcon.svelte
    │   │   │               └── Icon.svelte
    │   │   └── Icon.svelte
    │   ├── SurveyActions.svelte
    │   │   ├── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── StaticPanels.svelte
    │   │       └── StaticPanel.svelte
    │   │           ├── Markdown.svelte
    │   │           └── Icon.svelte
    │   ├── SurveyInstanceVersionPicker.svelte
    │   │   └── Icon.svelte
    │   └── DataExtractLink.svelte
    │       └── Icon.svelte
    ├── CopySurveyResponsesPanel.svelte
    │   ├── NoData.svelte
    │   ├── Icon.svelte
    │   ├── EntityLink.svelte
    │   │   ├── ViewLink.svelte
    │   │   └── EntityLabel.svelte
    │   │       └── EntityIcon.svelte
    │   │           └── Icon.svelte
    │   └── SearchInput.svelte
    │       └── Icon.svelte
    └── Markdown.svelte
```

**survey/SurveyTemplateImport.svelte**

```
├── ViewLink.svelte
├── PageHeader.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**survey/components/svelte/inline-panel/SurveyViewer.svelte**

```
├── SurveyQuestionResponse.svelte
│   ├── StringResponseRenderer.svelte
│   │   └── Markdown.svelte
│   ├── NumberResponseRenderer.svelte
│   ├── DateResponseRenderer.svelte
│   ├── EntityResponseRenderer.svelte
│   │   └── EntityLink.svelte
│   │       ├── ViewLink.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   ├── BooleanResponseRenderer.svelte
│   │   └── Icon.svelte
│   ├── DropdownMultiResponseRenderer.svelte
│   ├── MeasurableMultiResponseRenderer.svelte
│   │   └── MeasurableTree.svelte
│   │       └── EntityLink.svelte
│   │           ├── ViewLink.svelte
│   │           └── EntityLabel.svelte
│   │               └── EntityIcon.svelte
│   │                   └── Icon.svelte
│   └── LegalEntityResponseRenderer.svelte
│       └── EntityLink.svelte
│           ├── ViewLink.svelte
│           └── EntityLabel.svelte
│               └── EntityIcon.svelte
│                   └── Icon.svelte
├── SurveyViewerContextPanel.svelte
│   ├── Icon.svelte
│   ├── SurveyInstanceInfoPanel.svelte
│   │   ├── EntityLink.svelte
│   │   │   ├── ViewLink.svelte
│   │   │   └── EntityLabel.svelte
│   │   │       └── EntityIcon.svelte
│   │   │           └── Icon.svelte
│   │   ├── DateTime.svelte
│   │   ├── DescriptionFade.svelte
│   │   │   ├── Markdown.svelte
│   │   │   └── Icon.svelte
│   │   └── DatePicker.svelte
│   │       └── Icon.svelte
│   ├── SurveyPeople.svelte
│   │   ├── PersonList.svelte
│   │   │   ├── Icon.svelte
│   │   │   ├── EntityLabel.svelte
│   │   │   │   └── EntityIcon.svelte
│   │   │   │       └── Icon.svelte
│   │   │   └── EntitySearchSelector.svelte
│   │   │       └── EntityLabel.svelte
│   │   │           └── EntityIcon.svelte
│   │   │               └── Icon.svelte
│   │   └── Icon.svelte
│   ├── SurveyActions.svelte
│   │   ├── Icon.svelte
│   │   ├── NoData.svelte
│   │   ├── ViewLink.svelte
│   │   └── StaticPanels.svelte
│   │       └── StaticPanel.svelte
│   │           ├── Markdown.svelte
│   │           └── Icon.svelte
│   ├── SurveyInstanceVersionPicker.svelte
│   │   └── Icon.svelte
│   └── DataExtractLink.svelte
│       └── Icon.svelte
├── CopySurveyResponsesPanel.svelte
│   ├── NoData.svelte
│   ├── Icon.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   └── SearchInput.svelte
│       └── Icon.svelte
└── Markdown.svelte
```

**survey/components/svelte/inline-panel/SurveyViewerContextPanel.svelte**

```
├── Icon.svelte
├── SurveyInstanceInfoPanel.svelte
│   ├── EntityLink.svelte
│   │   ├── ViewLink.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── DateTime.svelte
│   ├── DescriptionFade.svelte
│   │   ├── Markdown.svelte
│   │   └── Icon.svelte
│   └── DatePicker.svelte
│       └── Icon.svelte
├── SurveyPeople.svelte
│   ├── PersonList.svelte
│   │   ├── Icon.svelte
│   │   ├── EntityLabel.svelte
│   │   │   └── EntityIcon.svelte
│   │   │       └── Icon.svelte
│   │   └── EntitySearchSelector.svelte
│   │       └── EntityLabel.svelte
│   │           └── EntityIcon.svelte
│   │               └── Icon.svelte
│   └── Icon.svelte
├── SurveyActions.svelte
│   ├── Icon.svelte
│   ├── NoData.svelte
│   ├── ViewLink.svelte
│   └── StaticPanels.svelte
│       └── StaticPanel.svelte
│           ├── Markdown.svelte
│           └── Icon.svelte
├── SurveyInstanceVersionPicker.svelte
│   └── Icon.svelte
└── DataExtractLink.svelte
    └── Icon.svelte
```

**system/svelte/ratings-schemes/SymbolPicker.svelte**

_No dependencies_

**report-grid/components/svelte/pickers/TagPicker.svelte**

```
├── Icon.svelte
└── NoData.svelte
```

**common/svelte/TagsInput.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/target-costs/TargetAppCostOverlayCell.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/widgets/target-costs/TargetAppCostWidgetParameters.svelte**

_No dependencies_

**playpen/3/builder/TaxonomyDiagramBuilder.svelte**

```
└── OverlayDiagramBuilder.svelte
    └── EntityGroupBox.svelte
        ├── GroupRow.svelte
        │   └── EntityCell.svelte
        │       ├── StatisticsBox.svelte
        │       └── CalloutBox.svelte
        └── CalloutBox.svelte
```

**system/svelte/nav-aid-builder/TaxonomyNavAidBuilder.svelte**

```
└── ColorPicker.svelte
```

**common/svelte/TextEditableField.svelte**

```
├── Markdown.svelte
├── SavingPlaceholder.svelte
│   └── Icon.svelte
└── Icon.svelte
```

**process-diagram/components/process-diagram/svg-elems/sub-types/Timer.svelte**

_No dependencies_

**notification/components/toaster/Toast.svelte**

```
└── Icon.svelte
```

**notification/components/toaster/Toasts.svelte**

```
├── Toast.svelte
│   └── Icon.svelte
└── Markdown.svelte
```

**common/svelte/Toggle.svelte**

```
└── Icon.svelte
```

**common/svelte/Tooltip.svelte**

_No dependencies_

**playpen/1/Unaware.svelte**

_No dependencies_

**system/svelte/nav-aid-builder/custom/Unit.svelte**

```
└── GroupLeader.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**measurable-rating/components/view-grid/UnmappedMeasurablesViewGrid.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── NoData.svelte
└── LoadingPlaceholder.svelte
    └── Icon.svelte
```

**entity-diagrams/components/entity-overlay-diagrams/builder/UpdateDiagramConfirmationPanel.svelte**

```
└── NoData.svelte
```

**technology/svelte/custom-environment-panel/UsageAssetInfo.svelte**

```
├── EntityLink.svelte
│   ├── ViewLink.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
└── Icon.svelte
```

**common/svelte/UsageCharacteristicsDecorator.svelte**

```
└── Icon.svelte
```

**technology/svelte/custom-environment-panel/UsageEdit.svelte**

```
├── EntitySearchSelector.svelte
│   └── EntityLabel.svelte
│       └── EntityIcon.svelte
│           └── Icon.svelte
├── Icon.svelte
├── UsageTree.svelte
│   ├── Icon.svelte
│   └── MiniActions.svelte
│       └── Icon.svelte
├── SearchInput.svelte
│   └── Icon.svelte
└── MiniActions.svelte
    └── Icon.svelte
```

**technology/svelte/custom-environment-panel/UsagePanel.svelte**

```
├── UsageEdit.svelte
│   ├── EntitySearchSelector.svelte
│   │   └── EntityLabel.svelte
│   │       └── EntityIcon.svelte
│   │           └── Icon.svelte
│   ├── Icon.svelte
│   ├── UsageTree.svelte
│   │   ├── Icon.svelte
│   │   └── MiniActions.svelte
│   │       └── Icon.svelte
│   ├── SearchInput.svelte
│   │   └── Icon.svelte
│   └── MiniActions.svelte
│       └── Icon.svelte
├── EnvironmentRemovalConfirmation.svelte
├── UsageTree.svelte
│   ├── Icon.svelte
│   └── MiniActions.svelte
│       └── Icon.svelte
├── UsageTable.svelte
│   ├── Icon.svelte
│   └── EntityLink.svelte
│       ├── ViewLink.svelte
│       └── EntityLabel.svelte
│           └── EntityIcon.svelte
│               └── Icon.svelte
├── MiniActions.svelte
│   └── Icon.svelte
└── UsageAssetInfo.svelte
    ├── EntityLink.svelte
    │   ├── ViewLink.svelte
    │   └── EntityLabel.svelte
    │       └── EntityIcon.svelte
    │           └── Icon.svelte
    └── Icon.svelte
```

**technology/svelte/custom-environment-panel/UsageTable.svelte**

```
├── Icon.svelte
└── EntityLink.svelte
    ├── ViewLink.svelte
    └── EntityLabel.svelte
        └── EntityIcon.svelte
            └── Icon.svelte
```

**technology/svelte/custom-environment-panel/UsageTree.svelte**

```
├── Icon.svelte
└── MiniActions.svelte
    └── Icon.svelte
```

**user/svelte/UserBulkEditor.svelte**

```
└── Icon.svelte
```

**user/svelte/UserCreatePanel.svelte**

_No dependencies_

**user/svelte/UserManagementPanel.svelte**

```
├── UserSelectList.svelte
│   ├── SearchInput.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   └── Icon.svelte
├── UserRolesList.svelte
│   ├── SearchInput.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   ├── Icon.svelte
│   └── ViewLink.svelte
├── UserCreatePanel.svelte
├── PasswordUpdatePanel.svelte
├── DeleteUserConfirmationPanel.svelte
│   └── NoData.svelte
├── Icon.svelte
└── UserBulkEditor.svelte
    └── Icon.svelte
```

**user/svelte/UserRolesList.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── NoData.svelte
├── Icon.svelte
└── ViewLink.svelte
```

**user/svelte/UserSelectList.svelte**

```
├── SearchInput.svelte
│   └── Icon.svelte
├── NoData.svelte
└── Icon.svelte
```

**survey/components/svelte/UserSurveyListPanel.svelte**

```
├── SurveyRecipientInfoPanel.svelte
│   ├── SurveyInstanceGrid.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   ├── Icon.svelte
│   └── SurveyViewer.svelte
│       ├── SurveyQuestionResponse.svelte
│       │   ├── StringResponseRenderer.svelte
│       │   │   └── Markdown.svelte
│       │   ├── NumberResponseRenderer.svelte
│       │   ├── DateResponseRenderer.svelte
│       │   ├── EntityResponseRenderer.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── BooleanResponseRenderer.svelte
│       │   │   └── Icon.svelte
│       │   ├── DropdownMultiResponseRenderer.svelte
│       │   ├── MeasurableMultiResponseRenderer.svelte
│       │   │   └── MeasurableTree.svelte
│       │   │       └── EntityLink.svelte
│       │   │           ├── ViewLink.svelte
│       │   │           └── EntityLabel.svelte
│       │   │               └── EntityIcon.svelte
│       │   │                   └── Icon.svelte
│       │   └── LegalEntityResponseRenderer.svelte
│       │       └── EntityLink.svelte
│       │           ├── ViewLink.svelte
│       │           └── EntityLabel.svelte
│       │               └── EntityIcon.svelte
│       │                   └── Icon.svelte
│       ├── SurveyViewerContextPanel.svelte
│       │   ├── Icon.svelte
│       │   ├── SurveyInstanceInfoPanel.svelte
│       │   │   ├── EntityLink.svelte
│       │   │   │   ├── ViewLink.svelte
│       │   │   │   └── EntityLabel.svelte
│       │   │   │       └── EntityIcon.svelte
│       │   │   │           └── Icon.svelte
│       │   │   ├── DateTime.svelte
│       │   │   ├── DescriptionFade.svelte
│       │   │   │   ├── Markdown.svelte
│       │   │   │   └── Icon.svelte
│       │   │   └── DatePicker.svelte
│       │   │       └── Icon.svelte
│       │   ├── SurveyPeople.svelte
│       │   │   ├── PersonList.svelte
│       │   │   │   ├── Icon.svelte
│       │   │   │   ├── EntityLabel.svelte
│       │   │   │   │   └── EntityIcon.svelte
│       │   │   │   │       └── Icon.svelte
│       │   │   │   └── EntitySearchSelector.svelte
│       │   │   │       └── EntityLabel.svelte
│       │   │   │           └── EntityIcon.svelte
│       │   │   │               └── Icon.svelte
│       │   │   └── Icon.svelte
│       │   ├── SurveyActions.svelte
│       │   │   ├── Icon.svelte
│       │   │   ├── NoData.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── StaticPanels.svelte
│       │   │       └── StaticPanel.svelte
│       │   │           ├── Markdown.svelte
│       │   │           └── Icon.svelte
│       │   ├── SurveyInstanceVersionPicker.svelte
│       │   │   └── Icon.svelte
│       │   └── DataExtractLink.svelte
│       │       └── Icon.svelte
│       ├── CopySurveyResponsesPanel.svelte
│       │   ├── NoData.svelte
│       │   ├── Icon.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   └── SearchInput.svelte
│       │       └── Icon.svelte
│       └── Markdown.svelte
├── SurveyApproverInfoPanel.svelte
│   ├── SurveyInstanceGrid.svelte
│   │   └── Icon.svelte
│   ├── NoData.svelte
│   ├── Icon.svelte
│   └── SurveyViewer.svelte
│       ├── SurveyQuestionResponse.svelte
│       │   ├── StringResponseRenderer.svelte
│       │   │   └── Markdown.svelte
│       │   ├── NumberResponseRenderer.svelte
│       │   ├── DateResponseRenderer.svelte
│       │   ├── EntityResponseRenderer.svelte
│       │   │   └── EntityLink.svelte
│       │   │       ├── ViewLink.svelte
│       │   │       └── EntityLabel.svelte
│       │   │           └── EntityIcon.svelte
│       │   │               └── Icon.svelte
│       │   ├── BooleanResponseRenderer.svelte
│       │   │   └── Icon.svelte
│       │   ├── DropdownMultiResponseRenderer.svelte
│       │   ├── MeasurableMultiResponseRenderer.svelte
│       │   │   └── MeasurableTree.svelte
│       │   │       └── EntityLink.svelte
│       │   │           ├── ViewLink.svelte
│       │   │           └── EntityLabel.svelte
│       │   │               └── EntityIcon.svelte
│       │   │                   └── Icon.svelte
│       │   └── LegalEntityResponseRenderer.svelte
│       │       └── EntityLink.svelte
│       │           ├── ViewLink.svelte
│       │           └── EntityLabel.svelte
│       │               └── EntityIcon.svelte
│       │                   └── Icon.svelte
│       ├── SurveyViewerContextPanel.svelte
│       │   ├── Icon.svelte
│       │   ├── SurveyInstanceInfoPanel.svelte
│       │   │   ├── EntityLink.svelte
│       │   │   │   ├── ViewLink.svelte
│       │   │   │   └── EntityLabel.svelte
│       │   │   │       └── EntityIcon.svelte
│       │   │   │           └── Icon.svelte
│       │   │   ├── DateTime.svelte
│       │   │   ├── DescriptionFade.svelte
│       │   │   │   ├── Markdown.svelte
│       │   │   │   └── Icon.svelte
│       │   │   └── DatePicker.svelte
│       │   │       └── Icon.svelte
│       │   ├── SurveyPeople.svelte
│       │   │   ├── PersonList.svelte
│       │   │   │   ├── Icon.svelte
│       │   │   │   ├── EntityLabel.svelte
│       │   │   │   │   └── EntityIcon.svelte
│       │   │   │   │       └── Icon.svelte
│       │   │   │   └── EntitySearchSelector.svelte
│       │   │   │       └── EntityLabel.svelte
│       │   │   │           └── EntityIcon.svelte
│       │   │   │               └── Icon.svelte
│       │   │   └── Icon.svelte
│       │   ├── SurveyActions.svelte
│       │   │   ├── Icon.svelte
│       │   │   ├── NoData.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── StaticPanels.svelte
│       │   │       └── StaticPanel.svelte
│       │   │           ├── Markdown.svelte
│       │   │           └── Icon.svelte
│       │   ├── SurveyInstanceVersionPicker.svelte
│       │   │   └── Icon.svelte
│       │   └── DataExtractLink.svelte
│       │       └── Icon.svelte
│       ├── CopySurveyResponsesPanel.svelte
│       │   ├── NoData.svelte
│       │   ├── Icon.svelte
│       │   ├── EntityLink.svelte
│       │   │   ├── ViewLink.svelte
│       │   │   └── EntityLabel.svelte
│       │   │       └── EntityIcon.svelte
│       │   │           └── Icon.svelte
│       │   └── SearchInput.svelte
│       │       └── Icon.svelte
│       └── Markdown.svelte
└── Icon.svelte
```

**system/svelte/version-info/VersionInfoPage.svelte**

```
├── PageHeader.svelte
│   └── Icon.svelte
├── ViewLink.svelte
└── Icon.svelte
```

**common/svelte/ViewLink.svelte**

_No dependencies_

**common/svelte/ViewLinkLabelled.svelte**

_No dependencies_

**flow-diagram/components/diagram-svelte/context-panel/VisibilityToggles.svelte**

_No dependencies_

**common/svelte/calendar-heatmap/Weeks.svelte**

_No dependencies_

**aggregate-overlay-diagram/components/aggregate-overlay-diagram/WidgetSelector.svelte**

```
└── Icon.svelte
```

**survey/components/svelte/inline-panel/Wrapper.svelte**

```
└── DataTypeTreeSelector.svelte
    ├── DataTypeTreeNode.svelte
    │   ├── Icon.svelte
    │   ├── RatingCharacteristicsDecorator.svelte
    │   │   └── FlowRatingCell.svelte
    │   ├── UsageCharacteristicsDecorator.svelte
    │   │   └── Icon.svelte
    │   ├── DataTypeNodeTooltipContent.svelte
    │   │   ├── Icon.svelte
    │   │   ├── RatingIndicatorCell.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── DescriptionFade.svelte
    │   │   │   ├── Markdown.svelte
    │   │   │   └── Icon.svelte
    │   │   ├── NoData.svelte
    │   │   └── Markdown.svelte
    │   └── Tooltip.svelte
    └── SearchInput.svelte
        └── Icon.svelte
```

