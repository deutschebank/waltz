import React, {useEffect, useState} from "react";
import {EntityReference} from "../../types/Entity";
import PageHeader from "../../components/common/page-header/PageHeader";
import {BreadCrumbsConfig} from "../../types/BreadCrumbs";
import FavouritesButton from "../../components/common/favourites-button/FavouritesButton";
import {useMutation, useQuery} from "@tanstack/react-query";
import {applicationApi} from "../../api/application";
import Loader from "../../components/common/loader/Loader";
import AppOverview from "../../components/application/AppOverview";
import {favouritesApi} from "../../api/favourites";
import {AppGroupEntry} from "../../types/AppGroup";
import Toast from "../../components/common/toast/Toast";

interface AppViewProps {
    parentEntityRef: EntityReference
}

export default function AppView({parentEntityRef}: AppViewProps) {
    const {isPending: appDataPending, data: application} = useQuery(applicationApi.getById(parentEntityRef.id));
    const {isPending: favouritesPending, data: favouritesData} = useQuery(favouritesApi.getFavouritesGroupEntries());
    const [favourites, setFavourites] = useState<AppGroupEntry[]>([]);

    const [error, setError] = useState<Error[]>([]);
    const [successMessages, setSuccessMessages] = useState<string[]>([]);

    useEffect(() => {
            if (favouritesData) {
                setFavourites(favouritesData);
            }
    }, [favouritesData]);

    const {isPending: addFavouritesPending, mutate: addFavouritesMutate} = useMutation<AppGroupEntry[], Error, number>({
        mutationFn: (applicationId: number) => {
            const {queryFn} = favouritesApi.addApplication(applicationId);
            return queryFn();
        },
        onError: (error) => {
            setError(prev=> prev.concat(error));
        },
        onSuccess: (data) => {
            setFavourites(data);
            setSuccessMessages(prev => prev.concat("Added to your favourites."));
        }
    });

    const {isPending: removefavouritesPending, mutate: removeFavouritesMutate} = useMutation<AppGroupEntry[], Error, number>({
        mutationFn: (applicationId: number) => {
            const {queryFn} = favouritesApi.removeApplication(applicationId);
            return queryFn();
        },
        onError: (error) => {
            setError(prev => prev.concat(error));
        },
        onSuccess: (data) => {
            setFavourites(data);
            setSuccessMessages(prev => prev.concat("Removed from your favourites."));
        }
    });

    const addFavourites = (applicationId: number) => {
        addFavouritesMutate(applicationId);
    }

    const removeFavourites = (applicationId: number) => {
        const thisFavourite = favourites
            .find(f => f.id === applicationId);

        if(thisFavourite && thisFavourite.isReadOnly) {
            setError(prev => prev.concat(Error("This app is a direct involvement and cannot be removed from favorites")));
        } else {
            removeFavouritesMutate(applicationId);
        }
    }

    const actions = <FavouritesButton parentEntityRef={parentEntityRef}
                                      favourites={favourites ?? []}
                                      addFavourite={() => addFavourites(parentEntityRef.id)}
                                      removeFavourite={() => removeFavourites(parentEntityRef.id)}/>

    const breadCrumbsConfig: BreadCrumbsConfig[] = [
        {href: "home", text: "Home"},
        {href: "application", text: "Applications"},
        {text: application?.name ?? "" }
    ]
    return (
        <PageHeader name={application?.name ?? ""}
                       icon={"desktop"}
                       small={application?.assetCode ?? ""}
                       breadcrumbs={breadCrumbsConfig}
                       actions={actions}>
            {appDataPending && <Loader/>}
            {application &&
                <AppOverview application={application}/>}
            {error && error.map(error => <Toast type={"ERROR"} message={error.message}/>)}
            {successMessages && successMessages.map(successMessage => <Toast type={"SUCCESS"} message={successMessage}/>)}
            {addFavouritesPending && <Toast type={"INFO"} message={"Adding to favourites."}/>}
            {removefavouritesPending && <Toast type={"INFO"} message={"Removing from favourites."}/>}
        </PageHeader>
    );
}