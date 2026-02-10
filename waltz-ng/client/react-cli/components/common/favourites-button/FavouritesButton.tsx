import React from "react";
import Icon from "../Icon";
import {EntityReference} from "../../../types/Entity";
import styles from "./FavouritesButton.module.css"
import {AppGroupEntry} from "../../../types/AppGroup";

interface FavouritesButtonProps {
    parentEntityRef: EntityReference;
    favourites: Partial<AppGroupEntry>[];
    addFavourite: any;
    removeFavourite: any;
}

const FavouritesButton: React.FC<FavouritesButtonProps> = ({parentEntityRef,
                                                                favourites,
                                                                addFavourite,
                                                                removeFavourite}) => {

    const favourite = favourites
        .find(f => f.id === parentEntityRef.id && f.kind === parentEntityRef.kind);

    const isFavourite = !!favourite;
    const isReadOnly = favourite?.isReadOnly;

    return (
        <div className={styles.favouritesButton + " " + (isReadOnly ? "text-muted" : "")}>
            {!isFavourite &&
                <span onClick={addFavourite}>
                    <Icon name="bookmark-o"/>
                </span>
            }

            {isFavourite &&
                <span onClick={removeFavourite}>
                    <Icon name="bookmark"/>
                </span>
            }
        </div>
    );
}

export default FavouritesButton