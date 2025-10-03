import React from 'react';

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
}) => {
    const classNames = [
        "fa",
        `fa-${name}`,
        flip ? `fa-flip-${flip}` : "",
        rotate ? `fa-rotate-${rotate}` : "",
        size ? `fa-${size}` : "",
        stack ? `fa-stack-${stack}` : "",
        fixedWidth ? "fa-fw" : "",
        inverse ? "fa-inverse" : "",
        spin ? "fa-spin" : "",
        pullLeft ? "fa-pull-left" : "",
        pullRight ? "fa-pull-right" : ""
    ].join(" ");

    return (
        <span className="icon" data-ux={name}>
            <i className={classNames}/>
        </span>
    );
};

export default Icon;
