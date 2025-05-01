import { Children } from "react";

function InfoSectionV2({ title, children }) {
    return (
        <div className="px-3">
            <div className="border-top py-5 border-secondary-subtle">
                {
                    title &&
                    <h4 style={{
                        fontSize: "24px",
                        marginBottom: "24px",
                        fontWeight: 400,
                    }}>{title}</h4>
                }
                {children}
            </div>
        </div>
    );
}

export default InfoSectionV2;


