import React, {useState, useEffect} from "react";
import styles from "./Tabs.module.scss";

type TabsItem = {
  id: string;
  label: string;
  icon?: React.ReactNode;
  dataTestId?: string;
  disabled?: boolean;
  renderPanel: () => React.ReactNode;
};

type TabsProps = {
  tabs: TabsItem[];
  defaultTabId?: string;
  onChange?: (tabId: string) => void;
};

/**
 * A dynamic and reusable Tabs component.
 * It manages its own state for the selected tab and renders the tabs and their content based on the props.
 */
const Tabs: React.FC<TabsProps> = ({tabs, defaultTabId, onChange}) => {
  // State to keep track of the currently selected tab's ID.
  // It initializes with the defaultTabId prop, or the ID of the first tab if defaultTabId is not provided.
  const [selectedTabId, setSelectedTabId] = useState<string>(defaultTabId ?? tabs[0]?.id);

  // Effect to update the selected tab when the defaultTabId prop changes.
  useEffect(() => {
    if (defaultTabId) {
      setSelectedTabId(defaultTabId);
    }
  }, [defaultTabId]);

  // Handler for when a tab is clicked.
  // It updates the internal state and calls the onChange callback if it exists.
  const handleTabChange = (tabId: string) => {
    setSelectedTabId(tabId);
    if (onChange) {
      onChange(tabId);
    }
  };

  // Finds the active tab object from the tabs array based on the selectedTabId.
  const activeTab = tabs.find((tab) => tab.id === selectedTabId);

  // Renders the tabs and the content of the active tab.
  return (
    <div className={styles.waltzTabs} style={{paddingTop: "1em"}}>
      {tabs.map((tab) => (
        <React.Fragment key={tab.id}>
          <input
            type="radio"
            name="group"
            checked={selectedTabId === tab.id}
            onChange={() => handleTabChange(tab.id)}
            id={tab.id}
            data-testid={tab.dataTestId}
            disabled={tab.disabled}
          />
          <label className={styles.wtlabel} htmlFor={tab.id}>
            <span>
              {tab.icon}
              {tab.label}
            </span>
          </label>
        </React.Fragment>
      ))}
      {/* Renders the content panel for the currently active tab. */}
      <div className={`${styles.wtTab} ${styles.wtActive}`}>
        {activeTab?.renderPanel()}
      </div>
    </div>
  );
};

export default Tabs;
