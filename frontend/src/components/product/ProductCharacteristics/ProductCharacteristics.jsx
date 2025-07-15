export default function ProductCharacteristics({ characteristics }) {
    if (!characteristics || characteristics.length === 0) return null;

    // Si hay más de 8 características, dividir en dos columnas
    const threshold = 8;
    const shouldSplit = characteristics.length > threshold;
    let firstHalf = characteristics;
    let secondHalf = [];
    if (shouldSplit) {
        const mid = Math.ceil(characteristics.length / 2);
        firstHalf = characteristics.slice(0, mid);
        secondHalf = characteristics.slice(mid);
    }

    return (
        <>
            <p className="pb-2 fw-semibold">Características generales</p>
            {shouldSplit ? (
                <div style={{ display: "flex", gap: "2rem", flexWrap: "nowrap", overflowX: "auto", maxWidth: "100%", alignItems: "flex-start" }}>
                    {[firstHalf, secondHalf].map((half, idx) => (
                        <table
                            key={idx}
                            className="table table-borderless table-striped w-50 rounded overflow-hidden"
                            style={{ fontSize: "0.7rem", minWidth: "220px", maxWidth: "100%", tableLayout: "fixed", wordBreak: "break-word" }}
                        >
                            <tbody>
                                {half.map(({ name, value }) => (
                                    <tr key={name}>
                                        <td className="fw-medium p-3 text-capitalize" style={{ wordBreak: "break-word" }}>{name}</td>
                                        <td className="p-3" style={{ wordBreak: "break-word" }}>{value}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ))}
                </div>
            ) : (
                <table className="table table-borderless table-striped w-50 rounded overflow-hidden" style={{ fontSize: "0.7rem", minWidth: "220px", maxWidth: "100%", tableLayout: "fixed", wordBreak: "break-word" }}>
                    <tbody>
                        {characteristics.map(({ id, name, value }) => (
                            <tr key={id}>
                                <td className="fw-medium p-3 text-capitalize" style={{ wordBreak: "break-word" }}>{name}</td>
                                <td className="p-3" style={{ wordBreak: "break-word" }}>{value}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </>
    );
}