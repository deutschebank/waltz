import React from 'react';
import Icon from './Icon';

interface SectionProps {
    icon?: string;
    name?: string;
    small?: string;
    children?: React.ReactNode;
}

const Section = ({
    icon = "fw",
    name,
    small,
    children }: SectionProps) => {
    return (
        <div>
            <div className="waltz-section">
                <div className="waltz-section-header">
                    <div className="waltz-visibility-parent waltz-section-header-title">
                        <Icon name={icon}/>
                        <span> {name}</span>
                        {small &&
                            <small>
                                <span> {small}</span>
                            </small>
                        }
                    </div>
                </div>
                <div className="container-fluid waltz-section-body animate-if">
                    {children}
                </div>
            </div>
        </div>
    );
};

export default Section;
