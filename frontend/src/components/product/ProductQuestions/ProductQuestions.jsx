import QuestionForm from "../../forms/QuestionForm/QuestionForm";
import QuestionsWithResponses from "../QuestionsWithResponses/QuestionsWithResponses";

export default function ProductQuestions({productId, productQuestions, addQuestion}) {
    return (
        <>
            <QuestionForm
                productId={productId}
                addQuestion={addQuestion}
            />
            <QuestionsWithResponses
                questions={productQuestions}
            />
        </>
    )
}