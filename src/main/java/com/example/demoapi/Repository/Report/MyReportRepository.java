package com.example.demoapi.Repository.Report;

import com.example.demoapi.DTO.User.ReportDTO;
import com.example.demoapi.Entity.Post.Comment;
import com.example.demoapi.Entity.Post.Post;
import com.example.demoapi.Entity.Post.React;
import com.example.demoapi.Entity.Post.Report;
import com.example.demoapi.Entity.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface MyReportRepository extends JpaRepository<Report, String> {
    @Query(value = "SELECT COUNT(*) AS report_count\n" +
                    "FROM [dbo].[tblReport]\n" +
                    "WHERE [isactive] = 1 and (postid = ?1 AND commentid IS NULL)\n" +
                    "OR (commentid = ?1 AND postid IS NULL);", nativeQuery = true)
    Integer countReport(String id);
    @Query(value = "select count(distinct [postid]) from [dbo].[tblReport] where [isactive] = 1", nativeQuery = true)
    Integer countPostByReport();
    @Query(value = "UPDATE [dbo].[tblReport]\n" +
                    "SET [isactive] = 0\n" +
                    "WHERE\n" +
                    "([commentid] IS NOT NULL AND [commentid] = ?1)\n" +
                    "    OR\n" +
                    "([postid] IS NOT NULL AND [postid] = ?1);", nativeQuery = true)
    @Modifying
    @Transactional
    Integer deleteReportById(String id);
    @Query(value = "select [postid], count([postid])\n" +
                    "from [dbo].[tblReport]\n" +
                    "where [isactive] = 1\n" +
                    "group by [postid]", nativeQuery = true)
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
    @Query(value = "select * from tblReport where postid = ?1",nativeQuery = true)
    List<Report> getReportsByPostId(String postid);

    @Query(value = "select count (id) from [dbo].[tblReport] where [isactive] = 1", nativeQuery = true)
    Integer countReport();
}
