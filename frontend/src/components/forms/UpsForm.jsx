import SecundaryBtn from "../basic/SecundaryBtn/SecundaryBtn";

export default function UpsForm({error, nextStage}) {

    return (
        <div className='bg-body w-50 mx-auto p-5 rounded text-center'>
            <h3>¡Ups! Algo salió mal</h3>
            <p className="fs-075 text-danger">{error}</p>
            <div className='mt-4'>
                <SecundaryBtn text='Volver a intentar' onClick={nextStage} />
            </div>
        </div>
    )
}