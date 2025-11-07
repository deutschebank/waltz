import React from "react";
import EntityLink from "../../common/entity/EntityLink";
import Icon from "../../common/Icon";
import {KeyPersonsAndRole} from "../../../types/Person";
import {mkRef} from "../../../utils/mkRef";

interface KeyPeopleContentProps {
  keyPeople: KeyPersonsAndRole[];
}

const KeyPeopleContent: React.FC<KeyPeopleContentProps> = ({ keyPeople }) => {
  return (
      <div style={{ padding: "5px 0" }}>
        <div style={{ minHeight: "1px" }}>
          <table className="waltz-field-table waltz-field-table-border" style={{ width: "100%" }}>
            <colgroup>
              <col width="30%" />
              <col width="70%" />
            </colgroup>
            <tbody>
              {keyPeople.map((item, index) => (
                <tr key={index}>
                  <td className="wft-label" style={{ padding: "3px" }}>
                    <span>{item.roleName}</span>
                  </td>
                  <td>
                    <div>
                      {item.persons.map((p, pIdx) => (
                        <span key={pIdx}>
                          <EntityLink ref={mkRef(p)}/> -
                          <a href={`mailto:${p.email}`} style={{ marginLeft: "4px" }}>
                            <Icon name="envelope-o"/>
                          </a>
                         {pIdx < item.persons.length - 1 && ", "}
                        </span>
                      ))}
                      {item.persons.length === 0 && (
                        <span className="wft-label small">Not defined</span>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
  );
};

export default KeyPeopleContent;
