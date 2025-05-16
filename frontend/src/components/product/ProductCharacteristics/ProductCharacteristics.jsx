export default function ProductCharacteristics({ characteristics }) {
    return (
        <>
            <p className="pb-2 fw-semibold">Características generales</p>

            <table className="table table-borderless table-striped w-50 rounded overflow-hidden">
                <tbody>
                    {characteristics?.map(({ id, name, value }) => (
                        <tr key={id}>
                            <td className="fw-medium p-3 text-capitalize">{name}</td>
                            <td className="p-3">{value}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
}