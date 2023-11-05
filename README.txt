	
    /** PROJECTS **/
    List<ProjectDto> getAllProjects()
	ProjectDto getProjectByName(String projectName)
	List<ProjectDto> getProjectsByResponsible(String responsible)
	addProject(ProjectDto projectDto)
    
	/** SUBSTANCES **/    
    List<String> getAllSubstances()
	SubstanceDto getSubstanceByName(String actNo)
	List<SubstanceDto> getSubstancesByFormula(String formula)
	addSubstance(SubstanceDto substanceDto)
    
    /** COMPOUNDS **/
	CompoundDto getCompoundByChemLabJournal(String chemLabJournal)
	List<CompoundDto> getCompoundsByCompoundName(String compoundName)
    
	/** BATCHES **/
    List<String> getAllBatches()
	BatchDto getBatchByChemLabJournal(String chemLabJournal)
	List<BatchDto> getBatchesByActNo(String actNo)
	List<BatchDto> getBatchesByChemist(String chemist)
	List<BatchDto> getBatchesByProducer(String producer)
	List<BatchDto> getBatchesByProject(String projectName)
	addBatch(BatchDto batchDto)
    
	/** SAMPLES **/
    List<String> getAllSamples()
	SampleDto getSampleBySampleId(Integer sampleId)
	List<SampleDto> getSamplesByActNo(String actNo)
	List<SampleDto> getSamplesByExtReference(String extReference)
	List<SampleDto> getSamplesBySupplier(String supplier)
	List<SampleDto> getSamplesByCatalogNo(String catalogNo)
	List<SampleDto> getSamplesByLocation(String location)
	List<SampleDto> getSamplesByUserId(String userId)
	addSample(SampleDto sampleDto)
	
    /** Employee **/
    List<EmployeeDto> getAllEmployees()
	EmployeeDto getEmployeeById(Integer id)
	EmployeeDto getEmployeeByUserId(String userId)
	List<EmployeeDto> getEmployeesByManagerId(Integer managerId)
	List<EmployeeGroupDto> getGroupsByUserId(String userId)
	EmployeeGroupDto getGroupByGroupName(String groupName)
	
	/** SEND Terminology **/
	List<SendTerminologyDto> getAllTerminolgy()
	List<String> getTerminologyByCode(String code)
	List<String> getSendTerminologyCodeListNames()
	String getSendTerminologyCodeByCodeName(String codeName)
	
