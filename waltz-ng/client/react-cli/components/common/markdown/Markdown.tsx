import React from "react";
import {markdownToHtml} from "../../../../common/markdown-utils";
import styles from "./Markdown.module.scss";
import {isEmpty, template} from "lodash";

interface MarkdownProps {
    text: string,
    context?: object,
    inline?: boolean
}

export default function Markdown({text, context = {}, inline = false}: MarkdownProps) {
    function mkHtml(text: string, context: object) {
        try {
            const markdownText = isEmpty(context)
                ? text
                : template(text, {
                    //@ts-ignore
                    variable: "context"
                })(context);  // creates template function then invokes with `ctx`

            return markdownToHtml(markdownText);
        } catch (e) {
            console.log("Failed to render markdown with context", { context, text, e })
        }
    }

    const convertedHtml = mkHtml(text, context);
    return (
        <span className={inline ? styles.inlineMarkdown : ""} dangerouslySetInnerHTML={{__html: convertedHtml}}>
        </span>
    )
}