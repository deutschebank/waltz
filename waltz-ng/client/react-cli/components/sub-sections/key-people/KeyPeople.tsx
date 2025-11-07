import React from "react";
import {EntityReference} from "../../../types/Entity";
import {involvementApi} from "../../../api/involvement-view";
import {useQuery} from "@tanstack/react-query";
import {KeyPersonsAndRole} from "../../../types/Person";
import {isEmpty} from "lodash";
import SubSection from "../../common/sub-section/SubSection";
import KeyPeopleContent from "./KeyPeopleContent";
import Toast from "../../common/toast/Toast";
import SubSectionLoader from "../../common/loader/sub-section/SubSectionLoader";
import KeyPeopleControls from "./KeyPeopleControls";

interface KeyPeopleProps {
  primaryEntityRef: EntityReference;
}

const KeyPeople: React.FC<KeyPeopleProps> = ({ primaryEntityRef }) => {
  const {
    data: keyInvolvements,
    isLoading,
    isError,
  } = useQuery(involvementApi.findKeyInvolvementsForEntity(primaryEntityRef));

  if (isLoading) {
    return <SubSectionLoader/>;
  }

  if (isError) {
    return <Toast type={"ERROR"} message={"Error loading key people."}/>;
  }

  if (isEmpty(keyInvolvements)) {
    return <></>;
  }

  const keyPeople =
    keyInvolvements?.reduce((acc: KeyPersonsAndRole[], involvementDetail) => {
        const roleName = involvementDetail.involvementKind.name;
        const existingRole = acc.find((item) => item.roleName === roleName);
        if (existingRole) {
          existingRole.persons.push(involvementDetail.person);
        } else {
          acc.push({ roleName, persons: [involvementDetail.person] });
        }
        return acc;
      }, []
    );

  const keyPeopleContent = <KeyPeopleContent keyPeople={keyPeople ?? []}/>

  return (
    <SubSection header="Key People"
                content={keyPeopleContent}
                controls={<KeyPeopleControls/>}/>
  );
};

export default KeyPeople;