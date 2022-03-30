/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nbl.services;

import com.nbl.pojo.Choice;
import com.nbl.pojo.Question;
import com.nbl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class QuestionServices {
    public List<Question> getQuestions(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE content like concat('%', ?, '%')");
            if (kw == null)
                kw = "";
            
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            
            List<Question> questions = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String content = rs.getString("content");
                int cateId = rs.getInt("category_id");
                questions.add(new Question(id, content, cateId));
            }
            
            return questions;
        }
    }
    
    public boolean addQuestion(Question q, List<Choice> choices) throws SQLException{
        if (choices.size() == 4) {
            try(Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("INSERT INTO question(id, content, category_id) VALUES(?, ?, ?)");
                stm.setString(1, q.getId());
                stm.setString(2, q.getContent());
                stm.setInt(3, q.getCategoryId());
                
                if(stm.executeUpdate() > 0) {
                    PreparedStatement stm1 = conn.prepareStatement("INSERT INTO choice(id, content, is_correct, question_id) VALUES(?, ?, ?, ?)");
                    
                    for(Choice c: choices){
                        stm1.setString(1, c.getId());
                        stm1.setString(2, c.getContent());
                        stm1.setBoolean(3, c.isCorrect());
                        stm1.setString(4, q.getId());
                        
                        stm1.executeUpdate();
                    }
                }
                
                conn.commit();
                
                return true;
            }
        }
        
        return false;
    }
    
    public Question getQuestionById(String questionId) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE id=?");
            stm.setString(1, questionId);
            ResultSet rs = stm.executeQuery();
            
            Question question = null;
            while(rs.next()) {
                question = new Question();
                question.setId(rs.getString("id"));
                question.setContent(rs.getString("content"));
                question.setCategoryId(rs.getInt("category_id"));
                break;
            }
            
            return question;
        }
    }
}
