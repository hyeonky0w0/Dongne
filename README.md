## 🚀 배포 및 인프라 아키텍처 (Deployment & Infrastructure)

GCP(Google Cloud Platform)의 서버리스 및 관리형 인프라를 활용하여 자동화된 프로덕션 환경을 구축 

AWS의 수동 인프라 설정 방식(EC2, Nginx, ALB 등)을 구조적으로 단순화하고 보안성과 확장성을 극대화


### 🏗️ Infrastructure Architecture
* **Frontend Entrypoint:** Cloud Load Balancing (무료 관리형 HTTPS/SSL 자동 적용)
* **Application Layer:** GCP Cloud Run (Knative 기반 컨테이너 서버리스 오케스트레이션)
* **Database Layer:** GCP Cloud SQL (MySQL 8.0, 구글 내부망 소켓 통신을 통한 인터넷 격리 보호)
* **CI/CD Pipeline:** GitHub ➡️ Cloud Build ➡️ Artifact Registry ➡️ Cloud Run 무중단 배포


---

### 🔄 CI/CD 배포 프로세스
완전 자동화된 지속적 통합 및 배포(CI/CD) 파이프라인이 구축

```text
[Local IntelliJ] ──(git push)──> [GitHub Repo]
                                      │
                                      ▼ (Webhook Trigger)
                                 [Cloud Build] ──(Dockerfile 빌드)──> [Artifact Registry]
                                                                             │
                                                                             ▼ (무중단 교체)
                                                                      [GCP Cloud Run]
