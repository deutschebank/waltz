import React from 'react';
import {FAFlip, FARotate, FASize, FAStack} from "../../types/FATypes";

interface IconProps {
    name?: string;
    size?: FASize;
    flip?: FAFlip;
    rotate?: FARotate;
    stack?: FAStack;
    fixedWidth?: boolean;
    inverse?: boolean;
    spin?: boolean;
    pullLeft?: boolean;
    pullRight?: boolean;
}

const Icon = ({
    name = "circle-o",
    size,
    flip,
    rotate,
    stack,
    fixedWidth = true,
    inverse,
    spin,
    pullLeft = false,
    pullRight = false
}: IconProps) => {
    const classNames = [
        "fa",
        `fa-${name}`,
        flip && `fa-flip-${flip}`,
        rotate && `fa-rotate-${rotate}`,
        size && `fa-${size}`,
        stack && `fa-stack-${stack}`,
        fixedWidth && "fa-fw",
        inverse && "fa-inverse",
        spin && "fa-spin",
        pullLeft && "fa-pull-left",
        pullRight && "fa-pull-right"
    ].filter(Boolean)
    .join(" ");

    return (
        <span className="icon" data-ux={name}>
            <i className={classNames}/>
        </span>
    );
};

export default Icon;
