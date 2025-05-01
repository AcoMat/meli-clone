import { useRef } from "react";
import LargeBlueButton from "../../basic/LargeBlueButton/LargeBlueButton";

function QuestionForm({ addQuestion }) {
    const form = useRef(null);

    const handleSendQuestion = () => {
        addQuestion(form.current.value);
        form.current.value = "";
    }

    return (
        <div className="d-flex gap-3 mb-4">
            <textarea
                ref={form}
                className="rounded"
                rows="1"
                style={{
                    resize: "vertical",
                    boxShadow: "0px 0px 4px 0px #00000026",
                    width: "85%",
                    fontSize: "1rem",
                    maxHeight: "4.5rem", 
                    overflowY: "auto",
                }}
            ></textarea>
            

            <div style={{ width: "15%" }}>
                <LargeBlueButton onClick={handleSendQuestion} text={"Preguntar"} />
            </div>
        </div>
    );
}

export default QuestionForm;