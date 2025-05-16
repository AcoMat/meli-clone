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
                <div style={{ display: "flex", gap: "2rem" }}>
                    {[firstHalf, secondHalf].map((half, idx) => (
                        <table
                            key={idx}
                            className="table table-borderless table-striped w-50 rounded overflow-hidden"
                            style={{ fontSize: "0.7rem" }}
                        >
                            <tbody>
                                {half.map(({ id, name, value }) => (
                                    <tr key={id}>
                                        <td className="fw-medium p-3 text-capitalize">{name}</td>
                                        <td className="p-3">{value}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ))}
                </div>
            ) : (
                <table className="table table-borderless table-striped w-50 rounded overflow-hidden" style={{ fontSize: "0.7rem" }}>
                    <tbody>
                        {characteristics.map(({ id, name, value }) => (
                            <tr key={id}>
                                <td className="fw-medium p-3 text-capitalize">{name}</td>
                                <td className="p-3">{value}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </>
    );
}