import React, {useState} from "react";
import {useSliceSelector} from "../../hooks/useSliceSelector";
import Icon from "../../components/common/Icon";
import UserSelectList from "../../components/user-management/UserSelectList";
import CreateUser from "../../components/user-management/CreateUser";
import DeleteUser from "../../components/user-management/DeleteUser";
import PasswordUpdate from "../../components/user-management/PasswordUpdate";
import UserRolesList from "../../components/user-management/UserRolesList";
import UserBulkEditor from "../../components/user-management/UserBulkEditor";
import Tabs from "../../components/common/tabs/Tabs";
import {Modes} from "../../enums/User";

// Defines the tabs for the user management panel.
const TABS = {
  SINGLE: "single",
  BULK: "bulk",
};

/**
 * UserManagementPanel is the main component for managing users,
 * providing both single-user and bulk-editing capabilities.
 */
const UserManagementPanel: React.FC = () => {
  // Retrieves the active mode from the Redux store to determine which single-user component to render.
  const activeMode = useSliceSelector((state) => state.userManagement.activeMode);

  /**
   * Renders the appropriate component for single-user management based on the active mode.
   */
  const renderSingleUserContent = () => {
    switch (activeMode) {
      case Modes.LIST:
        return <UserSelectList />;
      case Modes.DETAIL:
        return <UserRolesList />;
      case Modes.ADD:
        return <CreateUser />;
      case Modes.PASSWORD:
        return <PasswordUpdate />;
      case Modes.DELETE:
        return <DeleteUser />;
      default:
        return <UserSelectList />;
    }
  };

  const tabs = [
    {
      id: TABS.SINGLE,
      label: "Individual User Admin",
      icon: <Icon name="pencil-square-o" />,
      dataTestId: "single",
      renderPanel: renderSingleUserContent,
    },
    {
      id: TABS.BULK,
      label: "Bulk User Admin",
      icon: <Icon name="list" />,
      dataTestId: "bulk",
      renderPanel: () => <UserBulkEditor />,
    },
  ];

  // Main render method for the component, including tab navigation.
  return (
    <div>
      <Tabs tabs={tabs} defaultTabId={TABS.SINGLE} />
    </div>
  );
};

export default UserManagementPanel;
