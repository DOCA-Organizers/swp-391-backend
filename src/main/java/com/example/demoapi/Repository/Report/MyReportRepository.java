package com.example.demoapi.Repository.Report;

import com.example.demoapi.DTO.User.ReportDTO;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.React;
import com.example.demoapi.Entity.Post.Report;
import com.example.demoapi.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface MyReportRepository extends JpaRepository<Report, String> {
    @Query(value = "SELECT COUNT(*) AS report_count\n" +
                    "FROM [dbo].[tblReport]\n" +
                    "WHERE (postid = ?1 AND commentid IS NULL)\n" +
                    "OR (commentid = ?1 AND postid IS NULL);", nativeQuery = true)
    Integer countReport(String id);
    @Query(value = "select count(distinct [postId]) from [dbo].[tblReport]", nativeQuery = true)
    Integer countPostByReport();
    @Query(value = "select [postId], count([postId])\n" +
                    "from [dbo].[tblReport]\n" +
                    "group by [postId]", nativeQuery = true)
    List<Object[]> showListPostWithNumberOfReport();
    List<Report> findReportsByUserIdAndPostIdAndCommentId(User userId, Post postId, Comment commentId);

    default List<ReportDTO> generateReportList() {
        List<Object[]> data = showListPostWithNumberOfReport();
        List<ReportDTO> reportList = new ArrayList<>();
        for (Object[] row : data) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setPostId((String) row[0]);
            reportDTO.setNumOfPost((Integer) row[1]);
            reportList.add(reportDTO);
        }
        return reportList;
    }
}
