import React, { useState } from "react";
import styles from "./UserManagementPanel.module.scss";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import Icon from "../../components/common/Icon";
import UserSelectList from "../../components/user-management/UserSelectList";
import CreateUser from "../../components/user-management/CreateUser";
import DeleteUser from "../../components/user-management/DeleteUser";
import PasswordUpdate from "../../components/user-management/PasswordUpdate";
import UserRolesList from "../../components/user-management/UserRolesList";
import UserBulkEditor from "../../components/user-management/UserBulkEditor";
import { Modes } from "../../enums/User";

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
  // State to manage the currently selected tab (single or bulk user management).
  const [selectedTab, setSelectedTab] = useState(TABS.SINGLE);
  // Retrieves the active mode from the Redux store to determine which single-user component to render.
  const activeMode = useSliceSelector(
    (state) => state.userManagement.activeMode
  );

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

  // Main render method for the component, including tab navigation.
  return (
    <div className={styles.waltzTabs} style={{ paddingTop: "1em" }}>
      <input
        type="radio"
        name="group"
        checked={selectedTab === TABS.SINGLE}
        onChange={() => setSelectedTab(TABS.SINGLE)}
        id="single"
      />
      <label className={styles.wtlabel} htmlFor="single">
        <span>
          <Icon name="pencil-square-o" /> Individual User Admin
        </span>
      </label>
      <input
        type="radio"
        name="group"
        checked={selectedTab === TABS.BULK}
        onChange={() => setSelectedTab(TABS.BULK)}
        id="bulk"
      />
      <label className={styles.wtlabel} htmlFor="bulk">
        <span>
          <Icon name="list" /> Bulk User Admin
        </span>
      </label>
      <div className={`${styles.wtTab} ${styles.wtActive}`}>
        {selectedTab === TABS.SINGLE ? (
          renderSingleUserContent()
        ) : (
          <UserBulkEditor />
        )}
      </div>
    </div>
  );
};

export default UserManagementPanel;