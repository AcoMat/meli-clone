export default function ProductDescription({ description }) {    
    return (
        description &&
            <p style={{
                fontSize: "20px",
                fontWeight: "400",
                lineHeight: "27px",
                color: "rgba(0,0,0,.55)"
            }}>{description}</p>
    )
}