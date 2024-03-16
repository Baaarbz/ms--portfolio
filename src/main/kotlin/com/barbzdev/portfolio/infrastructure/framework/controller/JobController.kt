package com.barbzdev.portfolio.infrastructure.framework.controller

import com.barbzdev.portfolio.application.CreateNewJobRequest
import com.barbzdev.portfolio.application.CreateNewJobService
import com.barbzdev.portfolio.application.GetAllJobsService
import com.barbzdev.portfolio.application.GetJobsResponse
import com.barbzdev.portfolio.application.JobDataRequest
import com.barbzdev.portfolio.application.LinkRequest
import java.net.URI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/jobs")
class JobController(
  private val getAllJobsService: GetAllJobsService,
  private val createNewJobService: CreateNewJobService
) {

  @GetMapping
  fun getJobs(): ResponseEntity<List<HttpGetAllJobsResponse>> {
    return ResponseEntity.ok(getAllJobsService().map { it.toHttpGetAllJobsResponse() })
  }

  @PostMapping
  fun addJob(@RequestBody request: HttpCreateJobRequest): ResponseEntity<Unit> {
    val jobId = createNewJobService(request.toCreateNewJobRequest())
    return ResponseEntity.created(URI("/api/v1/jobs/${jobId.id}")).build();
  }

  @PutMapping("{id}")
  fun updateJob(@PathVariable id: String): ResponseEntity<List<GetJobsResponse>> {
    //TODO not implemented yet
    return ResponseEntity.internalServerError().build()
  }

  @DeleteMapping("{id}")
  fun deleteJob(@PathVariable id: String): ResponseEntity<List<GetJobsResponse>> {
    //TODO not implemented yet
    return ResponseEntity.internalServerError().build()
  }

  private fun HttpCreateJobRequest.toCreateNewJobRequest(): CreateNewJobRequest {
    val links = jobData.links?.map { LinkRequest(it.name, it.url) }
    val jobDataRequest = JobDataRequest(links, jobData.tags)

    return CreateNewJobRequest(
      companyName,
      description,
      role,
      companyUrl,
      startDate,
      endDate,
      jobDataRequest
    )
  }
}

data class HttpCreateJobRequest(
  val companyName: String,
  val companyUrl: String,
  val description: String,
  val role: String,
  val startDate: String,
  val endDate: String?,
  val jobData: HttpJobDataRequest,
)

data class HttpJobDataRequest(
  val links: List<HttpLinkRequest>?,
  val tags: List<String>,
)

data class HttpLinkRequest(
  val name: String,
  val url: String,
)

data class HttpGetAllJobsResponse(
  val id: String,
  val companyName: String,
  val role: String,
  val description: String,
  val companyUrl: String,
  val startDate: String,
  val endDate: String?,
  val jobData: HttpJobDataResponse,
  val isCurrentCompany: Boolean,
)

data class HttpJobDataResponse(
  val links: List<HttpLinkResponse>?,
  val tags: List<String>?
)

data class HttpLinkResponse(
  val name: String,
  val url: String
)

fun GetJobsResponse.toHttpGetAllJobsResponse(): HttpGetAllJobsResponse {
  val links = jobData.links?.map { HttpLinkResponse(it.name, it.url) }
  val jobDataResponse = HttpJobDataResponse(links, jobData.tags)

  return HttpGetAllJobsResponse(
    id,
    companyName,
    role,
    description,
    companyUrl,
    startDate,
    endDate,
    jobDataResponse,
    endDate == null
  )
}
