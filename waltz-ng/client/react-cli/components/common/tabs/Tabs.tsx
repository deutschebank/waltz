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

const Tabs: React.FC<TabsProps> = ({tabs, defaultTabId, onChange}) => {
  const [selectedTabId, setSelectedTabId] = useState<string>(defaultTabId ?? tabs[0]?.id);

  useEffect(() => {
    if (defaultTabId) {
      setSelectedTabId(defaultTabId);
    }
  }, [defaultTabId]);

  const handleTabChange = (tabId: string) => {
    setSelectedTabId(tabId);
    if (onChange) {
      onChange(tabId);
    }
  };

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
