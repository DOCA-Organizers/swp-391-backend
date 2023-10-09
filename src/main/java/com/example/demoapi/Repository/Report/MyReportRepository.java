package com.example.demoapi.Repository.Report;

import com.example.demoapi.Entity.Post.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyReportRepository extends JpaRepository<Report, String> {
}
