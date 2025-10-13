
import React, { useState, useMemo, useEffect } from "react";
import { actorStore } from "../../../svelte-stores/actor-store";
import { termSearch } from "../../../common";
// import _ from "lodash";
import PageHeader from "../../components/common/page-header/PageHeader";
import ViewLink from "../../components/common/view-link/ViewLink";
import SearchInput from "../../components/common/SearchInput";
import EntityLink from "../../components/common/entity/EntityLink";
import DataExtractLink from "../../components/common/data-extract-link/DataExtractLink";

import styles from "./ActorListView.module.scss";

interface Actor {
    id: number;
    name: string;
    description: string;
    isExternal: boolean;
    externalId?: string;
    externalString?: string;
}

const ActorListView = () => {
    const [actors, setActors] = useState<Actor[]>([]);
    const [qry, setQry] = useState("");

    useEffect(() => {
        const actorsCall = actorStore.findAll();
        const subscription = actorsCall.subscribe(result => {
            if (result.data) {
                const mappedActors = result.data
                    .map(d => ({...d, externalString: d.isExternal ? "External" : "Internal"}))
                    .sort((a, b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase()))

                setActors(mappedActors);
            }
        });

        return () => subscription.unsubscribe();
    }, []);

    const actorList = useMemo(() => {
        if (qry.length === 0) {
            return actors;
        }
        return termSearch(actors, qry, ["name", "description", "externalString"]);
    }, [actors, qry]);

    const breadCrumbs =
        <div slot="breadcrumbs">
            <ol className="waltz-breadcrumbs">
                <li>
                    <ViewLink state="main">Home</ViewLink>
                </li>
                <li>
                    <ViewLink state="main.actors.list">Actors</ViewLink>
                </li>
            </ol>
        </div>;

    return (
        <div>
            <PageHeader icon="user-circle"
                        name="Actor List"
                        breadcrumbs={breadCrumbs}/>

            <div className="waltz-page-summary waltz-page-summary-attach">
                <div className="waltz-display-section">
                    <div className="row">
                        <div className="col-sm-12">
                            <SearchInput value={qry} onChange={setQry} />
                            <br />
                            <div className="help-block pull-right small" style={{ fontStyle: "italic" }}>
                                {actorList.length === actors.length ?
                                    `Displaying all ${actors.length} actors` :
                                    `Displaying ${actorList.length} out of ${actors.length} actors`
                                }
                            </div>
                            <div className="waltz-scroll-region-500">
                                <table className="table table-condensed">
                                    <colgroup>
                                        <col width="30%" />
                                        <col width="35%" />
                                        <col width="10%" />
                                        <col width="25%" />
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Type</th>
                                            <th>External Id</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {actorList.map(actor => (
                                            <tr key={actor.id}>
                                                <td>
                                                    <EntityLink ref={actor} />
                                                </td>
                                                <td className="force-wrap">{actor.description}</td>
                                                <td>{actor.externalString}</td>
                                                <td className={styles.externalId}>{actor.externalId || "-"}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                            <br />
                            <div className="pull-right">
                                <DataExtractLink
                                    name="Export Actors"
                                    filename="Actors"
                                    extractUrl="actor/all"
                                    styling="link"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ActorListView;
