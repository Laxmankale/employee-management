package com.employee.service.impl;

import com.employee.dto.EmployeeRequestDTO;
import com.employee.dto.EmployeeResponseDTO;
import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO dto) {
        Employee employee = mapToEntity(dto);
        return mapToDTO(repository.save(employee));
    }

    @Override
    public List<EmployeeResponseDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return mapToDTO(employee);
    }

    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setName(dto.getFirstName());
        employee.setLastname(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPosition(dto.getDepartment());
        employee.setSalary(dto.getSalary());

        return mapToDTO(repository.save(employee));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Mapping methods
    private Employee mapToEntity(EmployeeRequestDTO dto) {
        Employee employee = new Employee();
        employee.setName(dto.getFirstName());
        employee.setLastname(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPosition(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        return employee;
    }

    private EmployeeResponseDTO mapToDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getName());
        dto.setLastName(employee.getLastname());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getPosition());
        dto.setSalary(employee.getSalary());
        return dto;
    }
}
