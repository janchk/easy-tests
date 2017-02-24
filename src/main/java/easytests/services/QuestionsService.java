package easytests.services;

import easytests.entities.AnswerInterface;
import easytests.entities.Question;
import easytests.entities.QuestionInterface;
import easytests.mappers.QuestionsMapper;
import easytests.mappers.AnswersMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author firkhraag
 */
@Service
public class QuestionsService {

    @Autowired
    private QuestionsMapper usersMapper;

    @Autowired
    private AnswersMapper answersMapper;

    public List<QuestionInterface> findAll() {
        return this.map(this.usersMapper.findAll());
    }

    public QuestionInterface find(Integer id) {
        return this.usersMapper.find(id);
    }

    public void save(QuestionInterface question) {
        if (question.getId() == null) {
            this.usersMapper.insert(question);
            for (AnswerInterface answer: question.getAnswers())
                this.answersMapper.insert(answer);
        }
        else
            this.usersMapper.update(question);
    }

    public void delete(QuestionInterface question) {
        this.usersMapper.delete(question);
        for (AnswerInterface answer: question.getAnswers())
            this.answersMapper.delete(answer.getId());
    }

    private List<QuestionInterface> map(List<Question> questionsList) {
        final List<QuestionInterface> resultQuestionList = new ArrayList(questionsList.size());
        for (Question question: questionsList) {
            resultQuestionList.add(question);
        }
        return resultQuestionList;
    }
}
