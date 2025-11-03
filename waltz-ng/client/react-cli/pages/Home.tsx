// src/pages/Home.tsx
import React, { useState } from "react";
import AliasControl from "../components/common/alias-control/AliasControl";
import ComplexityKindPicker from "../components/common/picker/ComplexityKindPicker";
import { ComplexityKind, CostKind, TableRow } from "../types/Grid";
import CostKindPicker from "../components/common/picker/CostKindPicker";
import AppGroupPicker from "../components/common/picker/AppGroupPicker";
import { mkRef } from "../utils/mkRef";

const Home: React.FC = () => {
    //Complexity Kind
    const [selectedComplexityKinds, setSelectedComplexityKinds] = useState<
        any[]
    >([
        {
            name: "Mike",
            description: "Lorem Ipsume text !",
        },
    ]); // Store for selected complexity kinds

    const onSelectComplexityKind = (complexityKind: TableRow) => {
        if (
            !selectedComplexityKinds.some((ck) => ck.id === complexityKind.id)
        ) {
            setSelectedComplexityKinds([
                ...selectedComplexityKinds,
                complexityKind,
            ]); // Append if not already selected
        }
    };

    // Filter logic for ComplexityKindPicker
    const complexityKindIds = selectedComplexityKinds.map((kind) => kind.id);
    const selectionFilter = (complexityKind: ComplexityKind) =>
        !complexityKindIds.includes(complexityKind.id);

    // Cost Kind
    const [selectedCostKinds, setSelectedCostKinds] = useState<any[]>([
        {
            name: "Mike",
            description: "Lorem Ipsume text !",
        },
    ]); // Store for selected Cost kinds

    const onSelectCostKind = (costKind: TableRow) => {
        if (!selectedCostKinds.some((ck) => ck.id === costKind.id)) {
            setSelectedCostKinds([...selectedCostKinds, costKind]); // Append if not already selected
        }
    };

    // Filter logic for CostKindPicker
    const costKindIds = selectedCostKinds.map((kind) => kind.id);
    const selectionFilterCost = (costKind: CostKind) =>
        !costKindIds.includes(costKind.id);

    return (
        <div>
            <h1>Home Page</h1>
            <p>
                This is a simple React app using TypeScript and best practices.
            </p>
            <div style={{ padding: "2rem" }}>
                <h3>Migrated React Components List</h3>
                <h5>1. Alias Control Component</h5>
                <AliasControl
                    parentEntityReference={mkRef({
                        kind: "ACTOR",
                        id: 1206,
                    })}
                    editable={true}
                />
                <h5>2. Complexity Kind Picker</h5>
                <ComplexityKindPicker
                    onSelect={onSelectComplexityKind}
                    selectionFilter={selectionFilter}
                />
                <h5>3. Cost Kind Picker</h5>
                <CostKindPicker
                    onSelect={onSelectCostKind}
                    subjectKind="APPLICATION"
                    selectionFilter={selectionFilterCost}
                />
                <h5>3. App Group Picker</h5>
                <AppGroupPicker
                    onSelect={onSelectComplexityKind}
                    selectionFilter={selectionFilter}
                />
            </div>
        </div>
    );
};

export default Home;
