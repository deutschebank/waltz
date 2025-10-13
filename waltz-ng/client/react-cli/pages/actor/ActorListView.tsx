import React, { useState, useMemo } from "react";
import PageHeader from "../../components/common/page-header/PageHeader";
import ViewLink from "../../components/common/view-link/ViewLink";
import SearchInput from "../../components/common/SearchInput";
import EntityLink from "../../components/common/entity/EntityLink";
import DataExtractLink from "../../components/common/data-extract-link/DataExtractLink";
import {useQuery} from "@tanstack/react-query";
import actorApi from "../../api/actor";
import styles from "./ActorListView.module.scss";
import {mkRef} from "../../utils/mkRef";
import {useDebounce} from "../../hooks/useDebounce";
import {locale} from "moment";

const ActorListView = () => {
    const [qry, setQry] = useState("");
    const debouncedQry = useDebounce(qry, 400);

    const {isPending, data} = useQuery(actorApi.findAll());
    const mappedActors = data?.map(d => ({...d, externalString: d.isExternal ? "External" : "Internal"}))
            .sort((a, b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase()));

    const actorList = useMemo(() => {
        if (debouncedQry.length === 0) {
            return mappedActors;
        }
        // return termSearch(mappedActors, qry, ["name", "description", "externalString"]);
        return mappedActors
            ?.filter(actor =>
                actor.name.toLowerCase().includes(debouncedQry.toLowerCase())
                || actor.description.toLowerCase().includes(debouncedQry.toLowerCase())
                || actor.externalString.toLowerCase().includes(debouncedQry.toLowerCase()));
    }, [mappedActors, debouncedQry]);

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
                                {actorList?.length === mappedActors?.length ?
                                    `Displaying all ${mappedActors?.length} actors` :
                                    `Displaying ${actorList?.length} out of ${mappedActors?.length} actors`
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
                                        {actorList?.map(actor => (
                                            <tr key={actor?.id}>
                                                <td>
                                                    <EntityLink ref={mkRef(actor)} />
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
