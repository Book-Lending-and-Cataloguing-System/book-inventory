# Ebenezer Library System

A console-based Book Lending & Cataloguing System for Ebenezer Community Library, built for DCIT308 group project.

## Setup
1. Clone the repository: `git clone <repository-url>`
2. Ensure Java JDK 17+ is installed.
3. Compile and run: `javac src/*.java src/*/*.java && java src.Main`
4. Data files are stored in `data/` (books.txt, borrowers.txt, transactions.txt).

## Modules
- **model/**: Book, Borrower, Transaction classes.
- **datastructures/**: BookInventory, BorrowerRegistry, LendingTracker, OverdueMonitor.
- **utils/**: FileHandler, SearchUtil, SortUtil.
- **reports/**: ReportGenerator for analytics.

## Collaboration
- Create a branch: `git checkout -b <feature-name>`
- Commit changes: `git commit -m "Description"`
- Push: `git push origin <feature-name>`
- Create pull requests for review.


UNIVERSITY OF GHANA
COLLEGE OF BASIC AND APPLIED SCIENCES
SCHOOL OF PHYSICAL AND MATHEMATICAL SCIENCES
DEPARTMENT OF COMPUTER SCIENCE
DCIT 306 – CLOUD COMPUTING
ID – 11027858

Q1. The CAP theorem states that a distributed system cannot simultaneously guarantee Consistency (C), Availability (A), and Partition Tolerance (P). When a network partition occurs (P), a system must choose to prioritize either Consistency or Availability.
The explanations below clarifies how two real-world cloud databases, Amazon DynamoDB and Google Cloud Firestore (Firebase), handle the CAP theorem
1. Amazon DynamoDB
Amazon DynamoDB is a fully managed, serverless NoSQL database service designed for high performance at any scale.
Which properties it prioritizes:
DynamoDB is generally considered an AP (Availability and Partition Tolerance) system. It prioritizes high availability and partition tolerance over strong consistency by default.
Availability (A): DynamoDB is designed for continuous availability. It automatically replicates data across multiple Availability Zones (AZs) within an AWS Region. Even if one node or an entire AZ becomes unavailable, DynamoDB continues to operate and serve requests.
Partition Tolerance (P): As a distributed system, DynamoDB inherently handles network partitions. It continues to function and process requests even when communication breaks between nodes or AZs.
Consistency (C): While DynamoDB offers strong consistency, its default behavior is eventual constancy.

When data might be inconsistent or unavailable:
Inconsistency (with Eventually Consistent Reads):
•	By default, when you perform a read operation, DynamoDB returns an eventually consistent response. This means that a read might not reflect the results of a recently completed write. 
•	Dada might be temporarily inconsistent(stale) for a short period (usually milliseconds) after a write, as the changes propagates to all replicas. If your application reads are immediately after a write, it might get the older data.
•	This is the trade-off for higher availability and lover latency, as DynamoDB doesn’t wait for all replicas to acknowledge a write before confirming it.

Unavailability (with Strong Consistent Reads):
DynamoDB offers an option for Strongly Consistent Reads: When you request a strongly consistent read, DynamoDB ensures that the response reflects all successful writes that occurred before the read.
To achieve this, DynamoDB might incur higher latency, and in the event of a network partition or replica failure that prevents it from reaching a quorum of up-to-date replicas, it might return an error or become unavailable for that specific read requests until consistency can be guaranteed. This is where it sacrifices availability for consistency.

Transactional Consistency: DynamoDB also supports ACID transactions for multiple items within and across tables, which provides strong, all-or-nothing consistency guarantees for those specific operations.

How it handles network partitioning:
•	DynamoDB’s architecture involves data being replicated across three or more storage nodes in different Availability Zones within an AWS Region.
•	When a network partition occurs, and a subset of nodes becomes isolated, DynamoDB continues to serve reads and writes from the available partitions.
•	For eventually consistent writes, a write is considered successful as long as a sufficient number of replicas (a quorum, though the exact quorum size is not always public but implies more than one) acknowledge the write. If a replica is partitioned, it will be updated later when connectivity is restored. This leads to temporary inconsistencies.
•	For strongly consistent reads, DynamoDB needs to verify that it’s reading the most up-to-date data from a majority of replicas. If a network partition prevents if from reaching a quorum, the strong read will fail or time out, thus sacrificing availability to guarantee consistency. 
•	DynamoDB uses internal mechanisms like hinted handoff and anti-entropy protocols to eventually reconcile data across partitions once network connectivity is restored, ensuring all replicas converge to the latest state. 

2. 	Google Cloud Firestore (Firebase)
Google Cloud Firestre is a flexible, NoSQL document database for mobile, web, and server development. It’s often used with Firebase.
Which properties it prioritizes:
Cloud Firestore is generally considered a CP (Consistency and Partition Tolerance) system. It prioritizes strong consistency and partition tolerance, potentially sacrificing availability in very rare, extreme partition scenarios to ensure data integrity.
Consistency (C): Firestore provides strong consistency guarantees. All reads always return the latest version of the data, and write operations are atomic. It ensures that all clients see the same data at the same time, maintaining a single, consistent view of the database. This is achieved through consensus protocols like Paxos/Raft among replicas
Partition Tolerance (P): As a distributed database, Firestore is built to withstand network partitions. It replicates data across multiple regions (for multi-region instances) or zones within a region (for single-region instances) and uses robust distributed consensus mechanisms. 
•	Availability (A): While highly available, in a theoretical extreme network partition where a quorum cannot be formed for a write or a read, Firestore would prioritize consistency by potentially making the affected operation unavailable rather than serving stale data. However, due to its multi-region/multi-zone replication and sophisticated consensus protocols, actual unavailability is exceedingly rare and usually transient.
When data might be inconsistent or unavailable:
•	Inconsistency: Firestore rarely allows inconsistency for reads. Its strong consistency model means that reads will always reflect the latest committed write.
•	Unavailability:
o	In the event of a severe, prolonged network partition that prevents a majority of replicas from communicating, write operations could be blocked until a quorum can be established.
o	Similarly, reads might experience higher latency or, in very rare worst-case scenarios, could time out if the system cannot confirm strong consistency.
o	However, its robust design (synchronous replication, Paxos/Raft consensus, multi-zone/multi-region deployment) means such unavailability is highly minimized and typically short-lived.
How it handles network partitioning:
•	Firestore replicates data synchronously across multiple zones (in a single-region deployment) or multiple regions (in a multi-region deployment).
•	It uses consensus algorithms (like Paxos) to ensure that all writes are committed across a majority of replicas before being acknowledged as successful. This ensures strong consistency.
•	When a network partition occurs, if a client attempts to write, and the system cannot achieve a write quorum across the available replicas due to the partition, the write operation will fail or block until communication is restored and a quorum can be formed. This prevents divergent data states.
•	Similarly, reads are directed to available replicas, but the internal consistency protocols ensure that only the latest committed data is returned. If no such data can be confidently determined as the latest due to a partition, the read might fail or wait.
•	For writes involving multiple documents or collections, Firestore uses distributed transactions with a two-phase commit protocol, ensuring atomicity and strong consistency even across partitioned data.



