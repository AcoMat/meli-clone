import responseIcon from '../../../assets/arrows/response-icon.svg'

function QuestionsWithResponses({ questions }) {
    return (
        <>
            <h5 className='mb-4'>Ultimas preguntas</h5>
            {questions?.length > 0 ?
                questions.map((question) => (
                    <div className={`mx-4`} key={question?.id}>
                        <p className={`mb-0 my-3`} index={question?.id}>{question?.text}</p>
                        {question?.response && <p className='px-1 text-secondary'><img className='px-2 opacity-50 pb-2' src={responseIcon} />{question?.response}</p>}
                    </div>

                ))
                :
                <p className="text-center text-secondary py-5">Este producto no tiene preguntas</p>
            }
        </>

    );
}

export default QuestionsWithResponses;