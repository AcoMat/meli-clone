import { Children } from "react";

function InfoSection({ title, children }) {
    return (  
        <div className="bg-body rounded p-3 mb-3">
            <h4 style={{ paddingBottom: "12px", borderBottom: "1px solid #00000040" }}>{title}</h4>
            {children}
        </div>
    );
}

export default InfoSection;


